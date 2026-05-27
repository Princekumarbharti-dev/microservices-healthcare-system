import {
  Alert,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Chip,
  Paper,
  Stack,
  TextField,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { useEffect, useMemo, useState } from "react";
import { diagnosisApi } from "../api/diagnosis.api";
import type { Diagnosis } from "../types/diagnosis";
import { bookmarkApi } from "../api/bookmark.api";
import { useAuth } from "../app/AuthContext";
import type { Bookmark } from "../types/bookmark";

export default function DiagnosisPage() {
  const a = useAuth();

  const [gender, setGender] = useState("");
  const [age, setAge] = useState("");
  const [bp, setBp] = useState("");
  const [pain, setPain] = useState("");

  const [data, setData] = useState<Diagnosis[]>([]);
  const [err, setErr] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [toast, setToast] = useState<string | null>(null);
  const [bms, setBms] = useState<Bookmark[]>([]);

  const filters = useMemo(
    () => ({ gender, age, bp, pain_type: pain }),
    [gender, age, bp, pain]
  );

  const bmSet = useMemo(() => {
    const s = new Set<string>();
    bms.forEach((x) => s.add(x.diagnosisId));
    return s;
  }, [bms]);

  const load = async () => {
    setErr(null);
    setLoading(true);
    try {
      const r = await diagnosisApi.list(filters);
      setData(r);

      if (a.isAuthed) {
        const my = await bookmarkApi.mine();
        setBms(my);
      } else {
        setBms([]);
      }
    } catch {
      setErr("Failed to load diagnosis data");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const clear = () => {
    setGender("");
    setAge("");
    setBp("");
    setPain("");
  };

  const addBm = async (id: string) => {
    setToast(null);

    if (bmSet.has(id)) return;

    try {
      await bookmarkApi.add(id);
      setToast("Bookmarked");

      setBms((p) => [
        ...p,
        {
          id: -Date.now(),
          diagnosisId: id,
          userId: a.userId ?? "",
          createdAt: new Date().toISOString(),
        } as Bookmark,
      ]);
    } catch (e: any) {
      setToast(
        e?.response?.status === 403
          ? "Only REGISTERED_USER can bookmark"
          : "Bookmark failed"
      );
    }
  };

  return (
    <Box>
      <Stack
        direction={{ xs: "column", sm: "row" }}
        alignItems={{ xs: "flex-start", sm: "center" }}
        justifyContent="space-between"
        spacing={1}
        sx={{ mb: 2 }}
      >
        <Box>
          <Typography variant="h5">Diagnosis</Typography>

          {a.isAuthed ? (
            <Typography variant="body2" sx={{ opacity: 0.7 }}>
              Browse diagnosis records and bookmark useful results.
            </Typography>
          ) : (
            <Typography variant="body2" sx={{ opacity: 0.7 }}>
              Browse diagnosis records. Login to bookmark results.
            </Typography>
          )}
        </Box>
      </Stack>

      {err ? (
        <Alert severity="error" sx={{ mb: 2 }}>
          {err}
        </Alert>
      ) : null}

      {toast ? (
        <Alert severity="info" sx={{ mb: 2 }}>
          {toast}
        </Alert>
      ) : null}

      <Paper
        sx={{
          p: 2,
          mb: 2,
          position: "sticky",
          top: 64,
          zIndex: 10,
          backgroundColor: "background.paper",
          boxShadow: 3,
          borderRadius: 3,
        }}
      >
        <Typography variant="subtitle1" sx={{ mb: 1 }}>
          Filters
        </Typography>

        <Box
          sx={{
            display: "grid",
            gap: 2,
            gridTemplateColumns: {
              xs: "1fr",
              sm: "repeat(4, 1fr)",
            },
          }}
        >
          <FormControl fullWidth>
            <InputLabel id="gender-label">Gender</InputLabel>
            <Select
              labelId="gender-label"
              label="Gender"
              value={gender}
              onChange={(e) => setGender(e.target.value)}
            >
              <MenuItem value="">All</MenuItem>
              <MenuItem value="Male">Male</MenuItem>
              <MenuItem value="Female">Female</MenuItem>
            </Select>
          </FormControl>

          <TextField
            fullWidth
            label="Age"
            placeholder="e.g. 55"
            value={age}
            onChange={(e) => setAge(e.target.value)}
          />

          <TextField
            fullWidth
            label="BP"
            placeholder="e.g. 140"
            value={bp}
            onChange={(e) => setBp(e.target.value)}
          />

          <TextField
            fullWidth
            label="Pain Type"
            placeholder="Typical Angina"
            value={pain}
            onChange={(e) => setPain(e.target.value)}
          />
        </Box>

        <Stack direction="row" spacing={1} sx={{ mt: 2 }}>
          <Button variant="contained" onClick={load} disabled={loading}>
            {loading ? "Loading..." : "Apply"}
          </Button>
          <Button variant="outlined" onClick={clear} disabled={loading}>
            Clear
          </Button>
        </Stack>
      </Paper>

      <Box
        sx={{
          display: "grid",
          gap: 2,
          gridTemplateColumns: {
            xs: "1fr",
            sm: "repeat(2, 1fr)",
            md: "repeat(3, 1fr)",
            lg: "repeat(4, 1fr)",
          },
        }}
      >
        {data.map((d) => (
          <Card
            key={d.id}
            sx={{
              height: "100%",
              display: "flex",
              flexDirection: "column",
              borderRadius: 3,
              boxShadow: 3,
            }}
          >
            <CardContent sx={{ flexGrow: 1 }}>
              <Stack
                direction="row"
                alignItems="center"
                justifyContent="space-between"
                spacing={1}
              >
                <Typography variant="h6">#{d.id}</Typography>
                <Chip label={d.gender} size="small" />
              </Stack>

              <Stack direction="row" spacing={1} sx={{ mt: 1, flexWrap: "wrap", rowGap: 1 }}>
                <Chip size="small" variant="outlined" label={`Age: ${d.age}`} />
                <Chip size="small" variant="outlined" label={`BP: ${d.bp}`} />
                <Chip
                  size="small"
                  variant="outlined"
                  label={`Chol: ${d.cholesterol}`}
                />
              </Stack>

              <Typography sx={{ mt: 1 }} variant="body2">
                Diabetic: <b>{d.diabetic}</b> • Smoking: <b>{d.smoking_status}</b>
              </Typography>

              <Typography sx={{ mt: 1 }} variant="body2">
                Pain: <b>{d.pain_type}</b>
              </Typography>

              <Typography sx={{ mt: 1 }} variant="body2">
                Treatment: <b>{d.treatment}</b>
              </Typography>
            </CardContent>

            <CardActions sx={{ px: 2, pb: 2 }}>
              <Button
                fullWidth
                variant="contained"
                onClick={() => addBm(d.id)}
                disabled={!a.isAuthed || bmSet.has(d.id)}
                title={
                  !a.isAuthed
                    ? "Login to bookmark"
                    : bmSet.has(d.id)
                    ? "Already bookmarked"
                    : ""
                }
              >
                {bmSet.has(d.id) ? "Bookmarked" : "Bookmark"}
              </Button>
            </CardActions>
          </Card>
        ))}
      </Box>

      {!loading && data.length === 0 ? (
        <Typography sx={{ mt: 3, opacity: 0.7 }}>
          No records found for given filters.
        </Typography>
      ) : null}
    </Box>
  );
}
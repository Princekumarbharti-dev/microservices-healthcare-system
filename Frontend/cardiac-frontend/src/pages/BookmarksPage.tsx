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
    Typography,
  } from "@mui/material";
  import { useEffect, useMemo, useState } from "react";
  import { bookmarkApi } from "../api/bookmark.api";
  import { diagnosisApi } from "../api/diagnosis.api";
  import type { Bookmark } from "../types/bookmark";
  import type { Diagnosis } from "../types/diagnosis";
  
  export default function BookmarksPage() {
    const [bms, setBms] = useState<Bookmark[]>([]);
    const [dx, setDx] = useState<Diagnosis[]>([]);
    const [err, setErr] = useState<string | null>(null);
    const [info, setInfo] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
  
    const load = async () => {
      setErr(null);
      setInfo(null);
      setLoading(true);
      try {
        const my = await bookmarkApi.mine();
        setBms(my);
  
        const all = await diagnosisApi.list({});
        setDx(all);
      } catch (e: any) {
        setErr(
          e?.response?.status === 403
            ? "Forbidden: only REGISTERED_USER"
            : "Failed to load bookmarks"
        );
      } finally {
        setLoading(false);
      }
    };
  
    useEffect(() => {
      load();
    }, []);
  
    const mapById = useMemo(() => {
      const m = new Map<string, Diagnosis>();
      dx.forEach((d) => m.set(d.id, d));
      return m;
    }, [dx]);
  
    const del = async (id: number) => {
      setErr(null);
      setInfo(null);
      try {
        await bookmarkApi.remove(id);
        setInfo("Deleted");
        setBms((p) => p.filter((x) => x.id !== id));
      } catch {
        setErr("Delete failed");
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
            <Typography variant="h5">My Bookmarks</Typography>
            <Typography variant="body2" sx={{ opacity: 0.7 }}>
              View and delete your saved diagnosis records.
            </Typography>
          </Box>
  
          <Chip
            label={loading ? "Loading..." : `${bms.length} saved`}
            variant="outlined"
          />
        </Stack>
  
        {err ? (
          <Alert severity="error" sx={{ mb: 2 }}>
            {err}
          </Alert>
        ) : null}
  
        {info ? (
          <Alert severity="success" sx={{ mb: 2 }}>
            {info}
          </Alert>
        ) : null}
  
        {bms.length === 0 && !loading ? (
          <Paper sx={{ p: 2 }}>
            <Typography sx={{ opacity: 0.7 }}>No bookmarks yet.</Typography>
          </Paper>
        ) : null}
  
        <Box
          sx={{
            display: "grid",
            gap: 2,
            gridTemplateColumns: {
              xs: "1fr",
              md: "repeat(2, 1fr)",
              lg: "repeat(3, 1fr)",
            },
          }}
        >
          {bms.map((b) => {
            const d = mapById.get(b.diagnosisId);
  
            return (
              <Card
                key={b.id}
                sx={{ height: "100%", display: "flex", flexDirection: "column" }}
              >
                <CardContent sx={{ flexGrow: 1 }}>
                  <Stack
                    direction="row"
                    alignItems="center"
                    justifyContent="space-between"
                    spacing={1}
                  >
                    <Typography variant="h6">Bookmark #{b.id}</Typography>
                    <Chip size="small" variant="outlined" label={`Dx: ${b.diagnosisId}`} />
                  </Stack>
  
                  <Typography variant="body2" sx={{ mt: 1, opacity: 0.7 }}>
                    Created: {b.createdAt}
                  </Typography>
  
                  {d ? (
                    <>
                      <Stack direction="row" spacing={1} sx={{ mt: 1, flexWrap: "wrap" }}>
                        <Chip size="small" label={d.gender} />
                        <Chip size="small" variant="outlined" label={`Age: ${d.age}`} />
                        <Chip size="small" variant="outlined" label={`BP: ${d.bp}`} />
                      </Stack>
  
                      <Typography sx={{ mt: 1 }} variant="body2">
                        Pain: <b>{d.pain_type}</b>
                      </Typography>
  
                      <Typography sx={{ mt: 1 }} variant="body2">
                        Treatment: <b>{d.treatment}</b>
                      </Typography>
                    </>
                  ) : (
                    <Typography sx={{ mt: 1, opacity: 0.7 }} variant="body2">
                      Diagnosis details not found.
                    </Typography>
                  )}
                </CardContent>
  
                <CardActions sx={{ px: 2, pb: 2 }}>
                  <Button
                    fullWidth
                    variant="outlined"
                    color="error"
                    onClick={() => del(b.id)}
                  >
                    Delete
                  </Button>
                </CardActions>
              </Card>
            );
          })}
        </Box>
      </Box>
    );
  }
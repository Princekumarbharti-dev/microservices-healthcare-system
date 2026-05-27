import { Alert, Box, Button, MenuItem, Paper, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { userProfileApi } from "../api/userprofile.api";
import { useAuth } from "../app/AuthContext";

export default function ProfilePage() {
  const a = useAuth();
  const uid = a.userId!;

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [age, setAge] = useState<number>(0);
  const [gender, setGender] = useState("MALE");

  const [msg, setMsg] = useState<string | null>(null);
  const [err, setErr] = useState<string | null>(null);
  const [fe, setFe] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);

  const load = async () => {
    setErr(null);
    setMsg(null);
    try {
      const p = await userProfileApi.getById(uid);
      setName(p.name || "");
      setEmail(p.email || "");
      setPhone(p.phone || "");
      setAge(p.age || 0);
      setGender(String(p.gender || "MALE"));
    } catch {
      setErr("Failed to load profile");
    }
  };

  useEffect(() => {
    load();
  }, []);

  const save = async () => {
    setErr(null);
    setMsg(null);
    setFe({});
    setLoading(true);

    try {
      await userProfileApi.update(uid, { name, phone, age, gender });
      setMsg("Profile updated");
    } catch (e: any) {
      const d = e?.response?.data;
      if (d && typeof d === "object") {
        const x: Record<string, string> = {};
        Object.keys(d).forEach((k) => {
          if (k !== "TimeStamp") x[k] = String(d[k]);
        });
        setFe(x);
        setErr(Object.values(x).join(", "));
      } else {
        setErr("Update failed");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        width: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        pt: 8
      }}
    >
      <Box sx={{ width: 560 }}>
        <Typography variant="h5" sx={{ mb: 2, textAlign: "center" }}>
          Profile
        </Typography>

        {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
        {msg && <Alert severity="success" sx={{ mb: 2 }}>{msg}</Alert>}

        <Paper sx={{ p: 3 }}>
          <TextField
            fullWidth
            label="Name"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
              setFe((p) => ({ ...p, name: "" }));
            }}
            sx={{ mb: 2 }}
            error={!!fe.name}
            helperText={fe.name || ""}
          />

          <TextField
            fullWidth
            label="Email"
            value={email}
            disabled
            sx={{ mb: 2 }}
          />

          <TextField
            fullWidth
            label="Phone"
            value={phone}
            onChange={(e) => {
              setPhone(e.target.value);
              setFe((p) => ({ ...p, phone: "" }));
            }}
            sx={{ mb: 2 }}
            error={!!fe.phone}
            helperText={fe.phone || ""}
          />

          <TextField
            fullWidth
            label="Age"
            type="number"
            value={age}
            onChange={(e) => {
              setAge(Number(e.target.value));
              setFe((p) => ({ ...p, age: "" }));
            }}
            sx={{ mb: 2 }}
            error={!!fe.age}
            helperText={fe.age || ""}
          />

          <TextField
            select
            fullWidth
            label="Gender"
            value={gender}
            onChange={(e) => {
              setGender(e.target.value);
              setFe((p) => ({ ...p, gender: "" }));
            }}
            sx={{ mb: 2 }}
            error={!!fe.gender}
            helperText={fe.gender || ""}
          >
            <MenuItem value="MALE">MALE</MenuItem>
            <MenuItem value="FEMALE">FEMALE</MenuItem>
          </TextField>

          <Button fullWidth variant="contained" onClick={save} disabled={loading}>
            {loading ? "Saving..." : "Save"}
          </Button>
        </Paper>
      </Box>
    </Box>
  );
}
import { Alert, Box, Button, MenuItem, Paper, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { userProfileApi } from "../api/userprofile.api";
import { useNavigate, Link as RLink } from "react-router-dom";

export default function RegisterPage() {
  const nav = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [age, setAge] = useState<number>(20);
  const [gender, setGender] = useState("MALE");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [msg, setMsg] = useState<string | null>(null);
  const [err, setErr] = useState<string | null>(null);
  const [fes, setFes] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);

  const submit = async () => {
    setErr(null);
    setMsg(null);
    setFes({});
    setLoading(true);

    try {
      await userProfileApi.register({
        name,
        email,
        phone,
        age,
        gender,
        password,
        confirmPassword,
      });
      setMsg("Registered successfully. Now login.");
      setTimeout(() => nav("/login"), 600);
    } catch (e: any) {
      const d = e?.response?.data;

      if (d && typeof d === "object") {
        const x: Record<string, string> = {};

        Object.keys(d).forEach((k) => {
          if (k !== "TimeStamp") x[k] = String(d[k]);
        });

        setFes(x);

        const g = Object.keys(x).length > 0 ? Object.values(x).join(", ") : "Registration failed";
        setErr(g);
      } else {
        setErr("Registration failed");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ maxWidth: 560, mx: "auto" }}>
      <Paper sx={{ p: 3 }}>
        <Typography variant="h5" sx={{ mb: 2 }}>
          Register
        </Typography>

        {err ? <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert> : null}
        {msg ? <Alert severity="success" sx={{ mb: 2 }}>{msg}</Alert> : null}

        <TextField
          fullWidth
          label="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          error={!!fes.name}
          helperText={fes.name || ""}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          error={!!fes.email}
          helperText={fes.email || ""}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="Phone"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          error={!!fes.phone}
          helperText={fes.phone || ""}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="Age"
          type="number"
          value={age}
          onChange={(e) => setAge(Number(e.target.value))}
          error={!!fes.age}
          helperText={fes.age || ""}
          sx={{ mb: 2 }}
        />

        <TextField
          select
          fullWidth
          label="Gender"
          value={gender}
          onChange={(e) => setGender(e.target.value)}
          error={!!fes.gender}
          helperText={fes.gender || ""}
          sx={{ mb: 2 }}
        >
          <MenuItem value="MALE">MALE</MenuItem>
          <MenuItem value="FEMALE">FEMALE</MenuItem>
        </TextField>

        <TextField
          fullWidth
          label="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          error={!!fes.password}
          helperText={fes.password || ""}
          sx={{ mb: 2 }}
        />

        <TextField
          fullWidth
          label="Confirm Password"
          type="password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          error={!!fes.confirmPassword}
          helperText={fes.confirmPassword || ""}
          sx={{ mb: 2 }}
        />

        <Button fullWidth variant="contained" onClick={submit} disabled={loading}>
          {loading ? "Creating..." : "Create Account"}
        </Button>

        <Typography sx={{ mt: 2 }} variant="body2">
          Already have account? <RLink to="/login">Login</RLink>
        </Typography>
      </Paper>
    </Box>
  );
}
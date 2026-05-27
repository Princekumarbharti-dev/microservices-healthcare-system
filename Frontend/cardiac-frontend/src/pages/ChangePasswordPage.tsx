import { Alert, Box, Button, Paper, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { authApi } from "../api/auth.api";

export default function ChangePasswordPage() {
  const [oldPassword, setOld] = useState("");
  const [newPassword, setNew] = useState("");
  const [confirmPassword, setC] = useState("");

  const [msg, setMsg] = useState<string | null>(null);
  const [err, setErr] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const submit = async () => {
    setErr(null);
    setMsg(null);
    setLoading(true);

    try {
      const r = await authApi.changePassword({
        oldPassword,
        newPassword,
        confirmPassword
      });

      setMsg(r.message || "Password updated");
      setOld("");
      setNew("");
      setC("");
    } catch (e: any) {
      const d = e?.response?.data;

      if (d?.message) {
        setErr(d.message);
      } else if (d && typeof d === "object") {
        const msgs = Object.keys(d)
          .filter((k) => k !== "TimeStamp")
          .map((k) => String(d[k]))
          .join(", ");

        setErr(msgs || "Password update failed");
      } else {
        setErr("Password update failed");
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
        pt: 10
      }}
    >
      <Box sx={{ width: 520 }}>
        <Typography variant="h5" sx={{ mb: 2, textAlign: "center" }}>
          Change Password
        </Typography>

        {err && <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert>}
        {msg && <Alert severity="success" sx={{ mb: 2 }}>{msg}</Alert>}

        <Paper sx={{ p: 3 }}>
          <TextField
            fullWidth
            label="Old Password"
            type="password"
            value={oldPassword}
            onChange={(e) => setOld(e.target.value)}
            sx={{ mb: 2 }}
          />

          <TextField
            fullWidth
            label="New Password"
            type="password"
            value={newPassword}
            onChange={(e) => setNew(e.target.value)}
            sx={{ mb: 2 }}
          />

          <TextField
            fullWidth
            label="Confirm Password"
            type="password"
            value={confirmPassword}
            onChange={(e) => setC(e.target.value)}
            sx={{ mb: 2 }}
          />

          <Button
            fullWidth
            variant="contained"
            onClick={submit}
            disabled={loading}
          >
            {loading ? "Updating..." : "Update Password"}
          </Button>
        </Paper>
      </Box>
    </Box>
  );
}
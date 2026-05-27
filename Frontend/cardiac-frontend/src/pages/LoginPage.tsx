import {
  Alert,
  Box,
  Button,
  Link,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { useState } from "react";
import { authApi } from "../api/auth.api";
import { useAuth } from "../app/AuthContext";
import { useNavigate, Link as RLink } from "react-router-dom";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [forgotMode, setForgotMode] = useState(false);
  const [forgotEmail, setForgotEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmNewPassword, setConfirmNewPassword] = useState("");

  const [err, setErr] = useState<string | null>(null);
  const [msg, setMsg] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const a = useAuth();
  const nav = useNavigate();

  const submitLogin = async () => {
    setErr(null);
    setMsg(null);
    setLoading(true);

    try {
      const r = await authApi.login({ email, password });
      a.login(r);
      nav("/");
    } catch (e: any) {
      const d = e?.response?.data;
      setErr(d?.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  const submitForgotPassword = async () => {
    setErr(null);
    setMsg(null);
    setLoading(true);

    try {
      const r = await authApi.forgotPassword({
        email: forgotEmail,
        newPassword,
        confirmPassword: confirmNewPassword,
      });

      setMsg(r.message || "Password reset successful");
      setForgotEmail("");
      setNewPassword("");
      setConfirmNewPassword("");
      setForgotMode(false);
    } catch (e: any) {
      const d = e?.response?.data;

      if (d?.message) {
        setErr(d.message);
      } else if (d && typeof d === "object") {
        const msgs = Object.keys(d)
          .filter((k) => k !== "TimeStamp")
          .map((k) => String(d[k]))
          .join(", ");
        setErr(msgs || "Password reset failed");
      } else {
        setErr("Password reset failed");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: "calc(100vh - 120px)",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        pt: 8,
      }}
    >
      <Box sx={{ width: 460 }}>
        <Typography variant="h5" sx={{ mb: 2, textAlign: "center" }}>
          {forgotMode ? "Forgot Password" : "Login"}
        </Typography>

        {err ? <Alert severity="error" sx={{ mb: 2 }}>{err}</Alert> : null}
        {msg ? <Alert severity="success" sx={{ mb: 2 }}>{msg}</Alert> : null}

        <Paper
          sx={{
            p: 3,
            borderRadius: "20px",
            boxShadow: "0 16px 40px rgba(15, 23, 42, 0.08)",
            border: "1px solid rgba(0,0,0,0.04)",
          }}
        >
          {!forgotMode ? (
            <>
              <TextField
                fullWidth
                label="Email"
                value={email}
                required
                onChange={(e) => setEmail(e.target.value)}
                sx={{ mb: 2 }}
              />

              <TextField
                fullWidth
                label="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                sx={{ mb: 1 }}
                required
              />

              <Box sx={{ textAlign: "right", mb: 2 }}>
                <Link
                  component="button"
                  type="button"
                  underline="hover"
                  onClick={() => {
                    setErr(null);
                    setMsg(null);
                    setForgotMode(true);
                  }}
                >
                  Forgot Password?
                </Link>
              </Box>

              <Button
                fullWidth
                variant="contained"
                onClick={submitLogin}
                disabled={loading}
                sx={{
                  py: 1.2,
                  borderRadius: "12px",
                  fontWeight: 700,
                }}
              >
                {loading ? "Signing in..." : "Sign In"}
              </Button>

              <Typography sx={{ mt: 2 }} variant="body2">
                New user? <RLink to="/register">Create account</RLink>
              </Typography>
            </>
          ) : (
            <>
              <TextField
                fullWidth
                label="Email"
                value={forgotEmail}
                onChange={(e) => setForgotEmail(e.target.value)}
                sx={{ mb: 2 }}
              />

              <TextField
                fullWidth
                label="New Password"
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                sx={{ mb: 2 }}
              />

              <TextField
                fullWidth
                label="Confirm New Password"
                type="password"
                value={confirmNewPassword}
                onChange={(e) => setConfirmNewPassword(e.target.value)}
                sx={{ mb: 2 }}
              />

              <Button
                fullWidth
                variant="contained"
                onClick={submitForgotPassword}
                disabled={loading}
                sx={{
                  py: 1.2,
                  borderRadius: "12px",
                  fontWeight: 700,
                }}
              >
                {loading ? "Updating..." : "Reset Password"}
              </Button>

              <Button
                fullWidth
                variant="text"
                onClick={() => {
                  setErr(null);
                  setMsg(null);
                  setForgotMode(false);
                }}
                sx={{ mt: 1, textTransform: "none" }}
              >
                Back to Login
              </Button>
            </>
          )}
        </Paper>
      </Box>
    </Box>
  );
}
import {
  AppBar,
  Avatar,
  Box,
  Button,
  Toolbar,
  Typography,
} from "@mui/material";
import FavoriteRoundedIcon from "@mui/icons-material/FavoriteRounded";
import MonitorHeartRoundedIcon from "@mui/icons-material/MonitorHeartRounded";
import PersonOutlineRoundedIcon from "@mui/icons-material/PersonOutlineRounded";
import BookmarkBorderRoundedIcon from "@mui/icons-material/BookmarkBorderRounded";
import LockResetRoundedIcon from "@mui/icons-material/LockResetRounded";
import LoginRoundedIcon from "@mui/icons-material/LoginRounded";
import AppRegistrationRoundedIcon from "@mui/icons-material/AppRegistrationRounded";
import LogoutRoundedIcon from "@mui/icons-material/LogoutRounded";
import { Link as RLink, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../app/AuthContext";
import { useEffect } from "react";
import { startInactivityLogout } from "../utils/inactivityLogout";

export default function AppLayout({ children }: { children: React.ReactNode }) {
  const a = useAuth();
  const nav = useNavigate();
  const loc = useLocation();

  useEffect(() => {
    if (!a.isAuthed) return;
    const stop = startInactivityLogout(() => {
      a.logout();
      nav("/login");
    });
    return stop;
  }, [a, a.isAuthed, nav]);

  const navBtnSx = (path: string) => ({
    color: "white",
    px: 2,
    py: 1,
    borderRadius: "12px",
    textTransform: "none",
    fontWeight: 600,
    bgcolor: loc.pathname === path ? "rgba(255,255,255,0.18)" : "transparent",
    border:
      loc.pathname === path
        ? "1px solid rgba(255,255,255,0.22)"
        : "1px solid transparent",
    transition: "all 0.25s ease",
    "&:hover": {
      bgcolor: "rgba(255,255,255,0.14)",
      transform: "translateY(-1px)",
    },
  });

  return (
    <Box
      sx={{
        minHeight: "100vh",
        background:
          "linear-gradient(180deg, #f4f8ff 0%, #f8fbff 30%, #ffffff 100%)",
      }}
    >
      <AppBar
        position="sticky"
        elevation={0}
        sx={{
          background: "rgba(0,0,0,0.88)",
          backdropFilter: "blur(10px)",
          boxShadow: "0 6px 20px rgba(0,0,0,0.15)",
          borderBottom: "1px solid rgba(255,255,255,0.08)",
        }}
      >
        <Toolbar
          sx={{
            minHeight: 72,
            px: 2,
            display: "flex",
            alignItems: "center",
          }}
        >
          <Box
            component={RLink}
            to="/"
            sx={{
              display: "flex",
              alignItems: "center",
              gap: 1.2,
              textDecoration: "none",
              color: "white",
              mr: 2,
              flexShrink: 0,
            }}
          >
           <Avatar
  sx={{
    bgcolor: "white",
    width: 42,
    height: 42,
    boxShadow: "0 8px 20px rgba(0,0,0,0.12)",
  }}
>
<FavoriteRoundedIcon sx={{ color: "#dc2626" }} />
</Avatar>

            <Box>
              <Typography
                variant="h6"
                sx={{
                  fontWeight: 800,
                  lineHeight: 1.1,
                  letterSpacing: 0.2,
                }}
              >
                Cardiac Diagnosis
              </Typography>
              <Typography
                variant="caption"
                sx={{ opacity: 0.88, display: "block", lineHeight: 1.1 }}
              >
                Smart Heart Health Assistant
              </Typography>
            </Box>
          </Box>

          <Box
            sx={{
              display: "flex",
              gap: 1,
              ml: 4,
              alignItems: "center",
            }}
          >
            <Button
              component={RLink}
              to="/"
              startIcon={<MonitorHeartRoundedIcon />}
              sx={navBtnSx("/")}
            >
              Diagnosis
            </Button>

            {a.isAuthed && a.role?.toUpperCase() === "REGISTERED_USER" ? (
              <>
                <Button
                  component={RLink}
                  to="/bookmarks"
                  startIcon={<BookmarkBorderRoundedIcon />}
                  sx={navBtnSx("/bookmarks")}
                >
                  Bookmarks
                </Button>

                <Button
                  component={RLink}
                  to="/profile"
                  startIcon={<PersonOutlineRoundedIcon />}
                  sx={navBtnSx("/profile")}
                >
                  Profile
                </Button>

                <Button
                  component={RLink}
                  to="/change-password"
                  startIcon={<LockResetRoundedIcon />}
                  sx={navBtnSx("/change-password")}
                >
                  Change Password
                </Button>
              </>
            ) : null}
          </Box>

          <Box sx={{ flex: 1 }} />

          {a.isAuthed ? (
            <Box
              sx={{
                display: "flex",
                alignItems: "center",
                gap: 1.5,
                px: 1,
                py: 0.7,
                borderRadius: "16px",
                bgcolor: "rgba(255,255,255,0.12)",
                border: "1px solid rgba(255,255,255,0.16)",
                flexShrink: 0,
              }}
            >
              <Avatar
                sx={{
                  width: 34,
                  height: 34,
                  bgcolor: "rgba(255,255,255,0.22)",
                  fontSize: 14,
                  fontWeight: 700,
                }}
              >
                {a.email?.charAt(0).toUpperCase()}
              </Avatar>

              <Typography
                variant="body2"
                sx={{
                  color: "white",
                  fontWeight: 500,
                  maxWidth: 220,
                  whiteSpace: "nowrap",
                  overflow: "hidden",
                  textOverflow: "ellipsis",
                }}
              >
                {a.email}
              </Typography>

              <Button
                color="inherit"
                startIcon={<LogoutRoundedIcon />}
                onClick={() => {
                  a.logout();
                  nav("/login");
                }}
                sx={{
                  textTransform: "none",
                  fontWeight: 700,
                  borderRadius: "12px",
                  px: 2,
                  bgcolor: "rgba(255,255,255,0.12)",
                  "&:hover": {
                    bgcolor: "rgba(255,255,255,0.2)",
                  },
                }}
              >
                Logout
              </Button>
            </Box>
          ) : (
            <Box sx={{ display: "flex", gap: 1, flexShrink: 0 }}>
              <Button
                component={RLink}
                to="/login"
                startIcon={<LoginRoundedIcon />}
                sx={{
                  color: "white",
                  textTransform: "none",
                  fontWeight: 700,
                  px: 2.2,
                  py: 1,
                  borderRadius: "12px",
                  bgcolor: "rgba(255,255,255,0.1)",
                  border: "1px solid rgba(255,255,255,0.18)",
                  "&:hover": {
                    bgcolor: "rgba(255,255,255,0.18)",
                  },
                }}
              >
                Login
              </Button>

              <Button
                component={RLink}
                to="/register"
                startIcon={<AppRegistrationRoundedIcon />}
                sx={{
                  color: "black",
                  bgcolor: "white",
                  textTransform: "none",
                  fontWeight: 800,
                  px: 2.2,
                  py: 1,
                  borderRadius: "12px",
                  boxShadow: "0 8px 20px rgba(0,0,0,0.12)",
                  "&:hover": {
                    bgcolor: "#f5f5f5",
                    transform: "translateY(-1px)",
                  },
                }}
              >
                Register
              </Button>
            </Box>
          )}
        </Toolbar>
      </AppBar>

      <Box sx={{ px: 3, py: 4 }}>
        <Box
          sx={{
            animation: "fadeInUp 0.35s ease",
            "@keyframes fadeInUp": {
              from: {
                opacity: 0,
                transform: "translateY(10px)",
              },
              to: {
                opacity: 1,
                transform: "translateY(0)",
              },
            },
          }}
        >
          {children}
        </Box>
      </Box>
    </Box>
  );
}
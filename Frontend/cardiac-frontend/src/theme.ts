import { createTheme } from "@mui/material/styles";

export const theme = createTheme({
  palette: {
    mode: "light",

    primary: {
      main: "#111827", // charcoal black
    },

    secondary: {
      main: "#374151",
    },
  },

  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: "none",
          fontWeight: 700,
          borderRadius: "10px",
        },

        containedPrimary: {
          backgroundColor: "#111827",
          color: "#fff",

          "&:hover": {
            backgroundColor: "#1f2937",
          },
        },
      },
    },
  },
});
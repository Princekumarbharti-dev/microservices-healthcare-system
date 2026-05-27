import { BrowserRouter, Route, Routes } from "react-router-dom";
import AppLayout from "./components/AppLayout";
import DiagnosisPage from "./pages/DiagnosisPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import BookmarksPage from "./pages/BookmarksPage";
import ProfilePage from "./pages/ProfilePage";
import ChangePasswordPage from "./pages/ChangePasswordPage";
import RequireAuth from "./app/RequireAuth";

export default function App() {
  return (
    <BrowserRouter>
      <AppLayout>
        <Routes>
          <Route path="/" element={<DiagnosisPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />

          <Route element={<RequireAuth registeredOnly />}>
            <Route path="/bookmarks" element={<BookmarksPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/change-password" element={<ChangePasswordPage />} />
          </Route>

          <Route path="*" element={<DiagnosisPage />} />
        </Routes>
      </AppLayout>
    </BrowserRouter>
  );
}
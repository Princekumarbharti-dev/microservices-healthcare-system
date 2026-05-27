export function startInactivityLogout(onLogout: () => void) {
    const mins = Number(import.meta.env.VITE_INACTIVITY_MINUTES ?? 10);
    const ms = Math.max(1, mins) * 60_000;
  
    let t: number | undefined;
  
    const reset = () => {
      if (t) window.clearTimeout(t);
      t = window.setTimeout(() => onLogout(), ms);
    };
  
    const ev = ["mousemove", "keydown", "click", "scroll", "touchstart"];
  
    ev.forEach((e) => window.addEventListener(e, reset, { passive: true }));
    reset();
  
    return () => {
      if (t) window.clearTimeout(t);
      ev.forEach((e) => window.removeEventListener(e, reset));
    };
  }
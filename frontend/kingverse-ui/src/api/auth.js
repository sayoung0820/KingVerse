
export async function login(username, password) {
  const body = new URLSearchParams({ username, password });
  const res = await fetch('/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body,
    credentials: 'include', // keep JSESSIONID cookie
  });
  if (!res.ok) throw new Error('Invalid credentials');
  return me();
}

/** Who am I? Returns {authenticated, username, roles:[...]} */
export async function me() {
  const res = await fetch('/api/auth/me', { credentials: 'include' });
  if (!res.ok) throw new Error('Failed to fetch session');
  return res.json();
}

/** Alias so existing code can import fetchCurrentUser */
export const fetchCurrentUser = me;

/** POST Spring Security logout; invalidates session. */
export async function logout() {
  const res = await fetch('/logout', { method: 'POST', credentials: 'include' });
  if (!res.ok) throw new Error('Logout failed');
  return { ok: true };
}

/** Wrapper for authenticated calls that need the session cookie. */
export function authedFetch(url, options = {}) {
  return fetch(url, { credentials: 'include', ...options });
}



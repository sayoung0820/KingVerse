
import { createContext, useContext, useEffect, useState } from 'react';
import { me, login as apiLogin, logout as apiLogout } from '../api/auth';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [auth, setAuth] = useState({ loading: true, authenticated: false });

  useEffect(() => {
    me().then(setAuth).catch(() => setAuth({ authenticated: false })).finally(() => {
      // ensure shape always has loading false
      setAuth(a => ({ ...a, loading: false }));
    });
  }, []);

  const login = async (username, password) => {
    const info = await apiLogin(username, password);
    setAuth(info);
    return info;
  };

  const logout = async () => {
    await apiLogout();
    setAuth({ authenticated: false });
  };

  const hasRole = (role) => auth?.roles?.includes(role) ?? false;

  return (
    <AuthContext.Provider value={{ auth, login, logout, hasRole }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}

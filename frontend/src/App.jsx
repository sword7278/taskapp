import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import './App.css'
import Navbar from './common/Navbar';
import Footer from './common/Footer';
import Register from './pages/Register';
import Login from './pages/Login';
import TasksPage from './pages/TasksPages';
import TaskFormPage from './pages/TaskFormPage';

function App() {

  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path='/register' element={<Register />} />
        <Route path='/login' element={<Login />} />
        <Route path="/tasks" element={<TasksPage />} />
        <Route path="/tasks/add" element={<TaskFormPage />} />
        <Route path="/tasks/edit/:id" element={<TaskFormPage />} />

        {/*Fall back Route for Unkown Pages*/}
        <Route path='*' element={<Navigate to="/tasks"/>} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;

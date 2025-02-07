import Btn from '../../props/button/Btn';
import { Link } from 'react-router-dom';
import { Container, Card, Form, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

import './LoginStyle.css';
import LoginScript from './LoginScript';

const Login = () => {
  const { handleInput, handleLogin, isLoading } = LoginScript();

  return (
    <Container className='d-flex align-items-center justify-content-center' style={{ height: '100vh' }}>
      <Card style={{ maxWidth: '500px', width: '100%' }}>
        <Card.Body>
          <h1 className='text-center'>Login</h1>
          <Form onSubmit={handleLogin}>
            <Form.Group className='mb-3'>
              <Form.Label>Correo electrónico:</Form.Label>
              <Form.Control onChange={handleInput} type="text" name='email' placeholder='Correo electrónico' />
            </Form.Group>
            <Form.Group className='mb-3'>
              <Form.Label>Contraseña:</Form.Label>
              <Form.Control onChange={handleInput} type="password" name='password' placeholder='Contraseña' />
            </Form.Group>
            <div className='d-flex justify-content-between'>
              <Button type='submit' className='btn-ingresar'>Ingresar</Button>
              <Link to='/'>
                <Button variant='outline-success' className='btn-volver'>Volver</Button>
              </Link>
            </div>
          </Form>
          {isLoading && <p className='text-center mt-3'>Cargando... espere unos minutos</p>}
        </Card.Body>
      </Card>
    </Container>
  );
}

export default Login;
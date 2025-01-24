import Btn from '../../props/button/Btn';

import './LoginStyle.css'
import LoginScript from './LoginScript';

const Login = () => {

  const { handleInput, handleLogin } = LoginScript();

  return (
    <div className='loginContainer'>
      <div className='loginCard'>
        <div className='loginTopContainer'>
          <h1>
            Login
          </h1>
        </div>
        <form onSubmit={handleLogin} className='loginBotContainer'>
          <div>
            <label htmlFor="">
              Correo electronico:
              <input onChange={handleInput} name='userName' type="text" />
            </label>
            <label htmlFor="">
              Contraseña:
              <input onChange={handleInput} name='password' type="password" />
            </label>
          </div>
          <div className='loginBtnContinaer'>
            <button>Ingresar</button>
            <Btn to={"/"} title={"volver"} />
          </div>
        </form>
      </div>
    </div>
  );
}

export default Login;
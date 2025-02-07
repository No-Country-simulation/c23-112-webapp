import DashboardScript from "./DashboardScript";
import './DashboardStyle.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const Dashboard = () => {
    const { jobs, users, renderList, RenderUsersList, RenderWorksList, RenderCreateWorks } = DashboardScript();

    return (
        <div className="Dashboard-container container-fluid">
            <div className="Dashboard-container-menu">
                <div className="Dashboard-but-menu">
                    <button className="btn-dasboard btn" onClick={RenderUsersList}>Ver usuarios</button>
                    <button className="btn-dasboard btn" onClick={RenderWorksList}>Ver trabajos</button>
                    <button className="btn-dasboard btn btn-create-work" onClick={RenderCreateWorks}>Crear trabajo</button>
                </div>
            </div>
            <div className="Dashboard-Vew">
                {renderList}
            </div>
        </div>
    );
}

export default Dashboard;
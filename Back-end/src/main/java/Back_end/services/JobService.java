package Back_end.services;

import Back_end.entities.Job;
import Back_end.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }


    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public Job updateJob(Long id, Job jobDetails) {
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job job = existingJob.get();
            job.setTitle(jobDetails.getTitle()); // Actualiza el título
            job.setCategory(jobDetails.getCategory()); // Actualiza la categoría
            job.setSalario(jobDetails.getSalario()); // Actualiza el salario
            job.setRestaurante(jobDetails.getRestaurante()); // Actualiza el restaurante
            job.setTurno(jobDetails.getTurno()); // Actualiza el turno
            job.setImagen(jobDetails.getImagen()); // Actualiza la imagen
            return jobRepository.save(job); // Guarda los cambios en el repositorio
        }
        return null;  // Retorna null si no se encuentra el trabajo
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}

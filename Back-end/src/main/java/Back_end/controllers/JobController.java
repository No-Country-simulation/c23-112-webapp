package Back_end.controllers;

import Back_end.entities.Job;
import Back_end.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // 🔓 Usuarios y Admins pueden ver trabajos
    @PreAuthorize("hasAnyRole('USER_0', 'ADMIN_1')")
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // 🔓 Usuarios y Admins pueden ver detalles de un trabajo
    @PreAuthorize("hasAnyRole('USER_0', 'ADMIN_1')")
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 🔒 Solo ADMIN puede crear trabajos
    @PreAuthorize("hasRole('ADMIN_1')")
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(job));
    }

    // 🔒 Solo ADMIN puede modificar trabajos
    @PreAuthorize("hasRole('ADMIN_1')")
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        return ResponseEntity.ok(jobService.updateJob(id, job));
    }

    // 🔒 Solo ADMIN puede eliminar trabajos
    @PreAuthorize("hasRole('ADMIN_1')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Trabajo eliminado correctamente");
    }
}


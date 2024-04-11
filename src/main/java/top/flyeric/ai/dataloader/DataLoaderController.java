package top.flyeric.ai.dataloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataLoaderController {

    private final DataLoaderService dataLoaderService;

    @Autowired
    public DataLoaderController(DataLoaderService dataLoaderService, JdbcTemplate jdbcTemplate) {
        this.dataLoaderService = dataLoaderService;
    }

    @PostMapping("/load")
    public ResponseEntity<String> load() {
        try {
            this.dataLoaderService.load();
            return ResponseEntity.ok("Data loaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while loading data: " + e.getMessage());
        }
    }

    @GetMapping("/count")
    public int count() {
        return dataLoaderService.count();
    }

    @DeleteMapping("/delete")
    public void delete() {
        dataLoaderService.delete();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred in the controller: " + e.getMessage());
    }
}

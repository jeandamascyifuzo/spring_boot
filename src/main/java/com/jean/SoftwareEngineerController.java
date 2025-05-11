package com.jean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spring")
public class SoftwareEngineerController {

    @Autowired
    private SoftwareEngineerRepo softwareEngineerRepo;

    @PostMapping
    public ResponseEntity<SoftwareEngineer> addSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
       SoftwareEngineer response =  softwareEngineerRepo.save(softwareEngineer);
       return ResponseEntity.ok(response);
    }

    @PostMapping("/insertMany")
    public List<SoftwareEngineer> insertManySoftwareEngineer(@RequestBody List<SoftwareEngineer> softwareEngineers) {
        return softwareEngineerRepo.saveAll(softwareEngineers);
    }

    @GetMapping
    public List<SoftwareEngineer> getEngineers(){
        List<SoftwareEngineer> response = softwareEngineerRepo.findAll();
        return response;
    };

    @GetMapping("/{userId}")
    public SoftwareEngineer getUserById(@PathVariable("userId") Integer userId){
        SoftwareEngineer response = softwareEngineerRepo.findById(userId).orElse(null);
        return response;

    }

    @PatchMapping("/{userId}")
    public ResponseEntity<SoftwareEngineer> updateUserById(
            @PathVariable("userId") Integer userId,
            @RequestBody UpdateSoftwareEngineerDTO updateDTO) {

        return softwareEngineerRepo.findById(userId)
                .map(existingUser -> {
                    // Only update fields that are not null
                    if (updateDTO.getName() != null) {
                        existingUser.setName(updateDTO.getName());
                    }
                    if (updateDTO.getTechStack() != null) {
                        existingUser.setTechStack(updateDTO.getTechStack());
                    }
                    softwareEngineerRepo.save(existingUser);
                    return ResponseEntity.ok(existingUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
        }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        if (softwareEngineerRepo.existsById(userId)) {
            softwareEngineerRepo.deleteById(userId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}

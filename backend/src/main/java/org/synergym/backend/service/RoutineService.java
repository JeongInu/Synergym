package org.synergym.backend.service;

import org.synergym.backend.dto.LikedExerciseDTO;
import org.synergym.backend.dto.RoutineDTO;
import org.synergym.backend.entity.Exercise;
import org.synergym.backend.entity.LikedExercise;
import org.synergym.backend.entity.Routine;
import org.synergym.backend.entity.User;

import java.util.List;

public interface RoutineService {
    RoutineDTO getRoutineById(Integer id);
    Integer addRoutine(RoutineDTO routineDTO);
    void updateRoutine(Integer id, RoutineDTO routineDTO);
    void deleteRoutine(Integer id);
    List<RoutineDTO> getAllRoutines();

    default Routine dtoToEntity(RoutineDTO dto, User user) {
        return Routine.builder()
                .id(dto.getId())
                .name(dto.getName())
                .routineGoal(dto.getRoutineGoal())
                .user(user)
                .useYN(dto.getUseYN())
                .build();
    }

    default RoutineDTO entityToDto(Routine routine) {
        return RoutineDTO.builder()
                .id(routine.getId())
                .name(routine.getName())
                .routineGoal(routine.getRoutineGoal())
                .userId(routine.getUser() != null ? routine.getUser().getId() : null)
                .useYN(routine.getUseYN())
                .build();
    }
}
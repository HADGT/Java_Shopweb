package spring.api.management.model.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.management.model.DTO.Roles;
import spring.api.management.model.Respositories.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    public Roles getRolesById(UUID id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<Roles> getRoleByName(String name) {
        return roleRepository.findByNameContaining(name);
    }

    public Roles createRoles(Roles role) {
        return roleRepository.save(role);
    }

    public Roles updateJobs(UUID id, String name) {
        Roles role = getRolesById(id);
        if (role == null) {
            logger.info("Dữ liệu công việc không tồn tại trong hệ thống!");
            return null;
        } else {
            if (name != null) {
                role.setRoleName(name);
            }
            logger.info("Cập nhật dữ liệu thành công!");
            return roleRepository.save(role);
        }
    }

    public void deleteRoles(UUID id) {
        roleRepository.deleteById(id);
    }
}


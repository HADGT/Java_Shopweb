package spring.api.management.seeder;

//@Component
public class LoginSeeder {//implements CommandLineRunner {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private JobRepository jobRepository;
//    @Autowired
//    private LocationRepository locationRepository;
//
//    // Tạo truy vấn JPQL để đếm số lượng bản ghi trong bảng employees
////    private boolean isEmptyTable() {
////        long count = (long) entityManager.createQuery("SELECT COUNT(e.employeeId) FROM employees e").getSingleResult();  // Lấy kết quả duy nhất
////        return count == 0; // bằng 0 trả về true hoặc ngược lại
////    }
//    @Transactional
//    @Override
//    public void run(String... args) { //throws Exception
//        List<roles> allRoles = roleRepository.findAll(); // Truy vấn danh sách từ database
//        UUID RoleId = allRoles.stream()
//                .filter(roles -> roles.getRoleName().equals("Admin"))
//                .map(roles::getRoleId)
//                .findFirst()
//                .orElse(null);
//        List<jobs> allJobs = jobRepository.findAll(); // Truy vấn danh sách từ database
//        UUID JobId = allJobs.stream()
//                .filter(jobs -> jobs.getJobTitle().equals("Giám đốc công nghệ (CTO)"))
//                .map(jobs::getJobId)
//                .findFirst()
//                .orElse(null);
//        List<locations> allLocates = locationRepository.findAll(); // Truy vấn danh sách từ database
//        UUID locateId = allLocates.stream()
//                .filter(locations -> locations.getStreetAddress().equals("Số 9 Xa La"))
//                .map(locations::getLocationId)
//                .findFirst()
//                .orElse(null);
//        // Duyệt qua danh sách provinces và gán regionId tương ứng
////        for (String value : roles) {
////            String locationName = value[0];  // Tên tỉnh/thành
////            String DistrictName = value[1];    // Tên khu vực (để tìm regionId)
////            String DistrictId = regionMap.get(DistrictName); // Tìm regionId theo tên khu vực
////
////            if (DistrictId != null) {
////            BigDecimal minSalary = new BigDecimal(value[1]);
////            BigDecimal maxSalary = new BigDecimal(value[2]);
//        employees pro = new employees("Trần Thị", "Bích", "Tr.ThBich@example.com", RoleId, "0982350616", JobId, "tbich123", locateId, null);
//        // Tạo Validator
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
////        // Kiểm tra validation
////        Set<ConstraintViolation<employees>> violations = validator.validate(pro);
////
////        // Nếu có lỗi, in ra
////        if (!violations.isEmpty()) {
////            for (ConstraintViolation<employees> violation : violations) {
////                System.out.println("Lỗi: " + violation.getMessage());
////            }
////        } else {
//////            employeeRepository.save(pro);
////            System.out.println("Dữ liệu hợp lệ!");
////        }
//
////            } else {
////                logger.warn("Không tìm thấy provinceName cho khu vực: {}", DistrictName);
////            }
//    }
////            employees employees = new employees(UUID.randomUUID(), "Nguyễn Quang", "Trung", "ngquangtrung@example.com", "aaa", "Aaa", "a", "a", "a", null);
////        } else {
////            String query = "SELECT * FROM employees";
////            String passwordEncode = passwordEncoder.encode("password");
////            logger.info("password: ({})", passwordEncode);
////        }
////    }
}
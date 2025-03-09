package java_project;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.*;
import java.util.regex.*;

class JobPortal {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> jobGivers = new HashMap<>(); 
    static Map<String, String[]> jobSeekers = new HashMap<>(); 
    static List<Job> jobs = new ArrayList<>();
    static String currentUserEmail; 

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to Job Portal");
            System.out.println("=====================");
            System.out.println("1. Job Giver");
            System.out.println("2. Job Seeker");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> jobGiverMenu();
                case 2 -> jobSeekerMenu();
                case 3 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void jobGiverMenu() {
        System.out.println("\nJob Giver Menu:");
        System.out.println("=================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> jobGiverRegister();
            case 2 -> jobGiverLogin();
            default -> System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    static void jobGiverRegister() {
        System.out.println("\nRegister below");
        System.out.println("----------------");
        String email;

        // Ensure valid email input
        while (true) {
            System.out.print("Email   : ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
            	System.out.println("Valid email address!");
            	
                break; // Exit loop if email is valid
            }
            System.out.println("Invalid email format. Please try again.");
        }

        String password;

        // Ensure valid password input
        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            if (isValidPassword(password)) {
            	System.out.println("Valid password!");
                break; // Exit loop if password is valid
            }
            System.out.println("Invalid password. It must be at least 6 characters long, include one uppercase letter, one lowercase letter, one digit, and one special character. Please try again.");
        }

        // Store the job giver details
        jobGivers.put(email, password);
        System.out.println("Registration Successful! Now, please log in.");
        jobGiverLogin(); // Proceed to login after successful registration
    }

    static void jobGiverLogin() {
    	System.out.println("\nLogin");
    	System.out.println("-------");
        System.out.print("Email   : ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (jobGivers.containsKey(email) && jobGivers.get(email).equals(password)) {
            System.out.println("Login Successful!");
            jobGiverPostMenu();
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    static void jobGiverPostMenu() {
        while (true) {
            System.out.println("\nJob Giver Menu:");

            System.out.println("=================");
            System.out.println("1. Post a Job");
            System.out.println("2. Log Out");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> postJob();
                case 2 -> {
                    System.out.println("\nLogged out successfully. Returning to Home Screen...");
                    System.out.println();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void postJob() {
    	System.out.print("\nPost a job");
    	System.out.print("\n------------------");
        System.out.print("\nJob Title                 : ");
        String title = scanner.nextLine();
        System.out.print("Required Skills             : ");
        String skills = scanner.nextLine();
        System.out.print("Experience Required(in yrs) : ");
        String experience = scanner.nextLine();
        System.out.print("Application Deadline        : ");
        String deadline = scanner.nextLine();
        System.out.print("Company Name                : "); 
        String companyName = scanner.nextLine(); 

        // Correctly create the Job object by passing fields in the right order
        jobs.add(new Job(title, skills, experience, deadline, companyName)); 
        System.out.println("Job posted successfully!");
    }

    static void jobSeekerMenu() {
        System.out.println("\nJob Seeker Menu");
        System.out.println("=================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> jobSeekerRegister();
            case 2 -> jobSeekerLogin();
            default -> System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    static void jobSeekerRegister() {
        System.out.println("\nRegister below");
        System.out.println("----------------");
        
        // Email validation
        System.out.print("Email         : ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please try again.");
            return;
        } else {
            System.out.println("Valid email address!");
        }

        String password;
        // Password validation
        while (true) {
            System.out.print("Password       : ");
            password = scanner.nextLine();
            if (isValidPassword(password)) {
                System.out.println("Valid password!");  // Print confirmation of valid password
                break;
            } else {
                System.out.println("Password must be at least 6 characters long and include an uppercase letter, a number, and a special character.");
            }
        }

        // Phone validation
        System.out.print("Phone Number    : ");
        String phone = scanner.nextLine();
        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number. It must be exactly 10 digits.");
            return;
        }

        // Experience input
        System.out.print("Experience Level(in yrs) : ");
        String experience = scanner.nextLine();

        // Store the job seeker details
        jobSeekers.put(email, new String[]{password, phone, experience});
        System.out.println("Registration Successful! Now, please log in.");

        // Call login method after successful registration
        jobSeekerLogin();
    }

    static void jobSeekerLogin() {
    	System.out.println("\nLogin");
        System.out.println("--------");
        System.out.print("Email   : ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (jobSeekers.containsKey(email) && jobSeekers.get(email)[0].equals(password)) {
            currentUserEmail = email; 
            System.out.println("Login Successful!");
            jobSeekerSearchMenu();
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }


    static void jobSeekerSearchMenu() {
        while (true) {
            System.out.println("\nJob Seeker Menu:");
            System.out.println("-------------------");
            System.out.println("1. Search for Jobs");
            System.out.println("2. Log Out");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> searchJobs();
                case 2 -> {
                    System.out.println("Logged out successfully. Returning to Home Screen...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void searchJobs() {
        System.out.print("\nEnter your skills : ");
        String[] skills = scanner.nextLine().split(",\\s*");

        String[] seekerDetails = jobSeekers.get(currentUserEmail);
        if (seekerDetails == null) {
            System.out.println("Please log in first.");
            return;
        }

        String seekerExperience = seekerDetails[2]; // Get seeker experience
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("\nMatching Jobs:");
        boolean jobsExpired = false; // Tracks if any jobs exist but are expired
        boolean skillMismatch = true; // Tracks if jobs failed due to skill mismatch
        boolean experienceMismatch = true; // Tracks if jobs failed due to insufficient experience
        boolean validJobsFound = false; // Tracks if at least one valid job is found

        for (Job job : jobs) {
            try {
                LocalDate jobDeadline = LocalDate.parse(job.deadline, formatter);

                // Check if the job deadline has passed
                if (jobDeadline.isBefore(today)) {
                    System.out.println("Deadline over for Job: " + job.title + " at " + job.companyName);
                    jobsExpired = true;
                    continue; // Skip expired jobs
                }

                // Check if the user's skills match the job's required skills
                if (!job.matchesSkills(skills)) {
                    continue; // Skip jobs if skills don't match
                }

                // Check if the user's experience meets the job's required experience
                int requiredExperience = Integer.parseInt(job.experience); // Convert job experience to integer
                int seekerExperienceYears = Integer.parseInt(seekerExperience); // Convert seeker's experience to integer
                if (seekerExperienceYears < requiredExperience) {
                    System.out.println("Your experience is insufficient for Job: " + job.title + " at " + job.companyName);
                    experienceMismatch = true;
                    continue; // Skip jobs if experience is insufficient
                }

                // If all criteria match, display the job
                validJobsFound = true;
                skillMismatch = false;
                experienceMismatch = false;
                System.out.println(job); // Display matching job details
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for job: " + job.title);
            } catch (NumberFormatException e) {
                System.out.println("Invalid experience format for job: " + job.title);
            }
        }

        // Provide feedback based on the results
        if (!validJobsFound) {
            if (jobsExpired && skillMismatch && experienceMismatch) {
                System.out.println("No valid jobs found. Some jobs are expired, others do not match your skills, and your experience does not meet the requirements.");
            } else if (jobsExpired) {
                System.out.println("No valid jobs found. All available jobs are expired.");
            } else if (skillMismatch) {
                System.out.println("No matching jobs found with the entered skills.");
            } else if (experienceMismatch) {
                System.out.println("No matching jobs found due to insufficient experience.");
            }
        } else {
            System.out.print("\nDo you want to apply for a job? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                System.out.print("\nEnter job title: ");
                String selectedJobTitle = scanner.nextLine();
                Job selectedJob = null;

                for (Job job : jobs) {
                    if (job.title.equalsIgnoreCase(selectedJobTitle)) {
                        selectedJob = job;
                        break;
                    }
                }

                if (selectedJob == null) {
                    System.out.println("Job not found. Returning to menu.");
                    return;
                }

                applyForJob(selectedJob, String.join(", ", skills));
            }
        }
    }





    static void applyForJob(Job job, String skills) {
        if (currentUserEmail == null || currentUserEmail.isEmpty()) {
            System.out.println("Please log in first.");
            return;
        }

        String[] seekerDetails = jobSeekers.get(currentUserEmail);  // Get seeker details based on email

        if (seekerDetails == null) {
            System.out.println("User not found. Please log in first.");
            return;
        }

        String phone = seekerDetails[1];  // Phone number
        String experience = seekerDetails[2];  // Experience level

        // Confirm the job details before applying
        System.out.println("\nConfirm Job Details:");
        System.out.println("------------------------------------");
        System.out.println("Job Title           : " + job.title);
        System.out.println("Company             : " + job.companyName);  // Show company name
        System.out.println("Required Skills     : " + job.skills);
        System.out.println("Experience Required : " + job.experience);
        System.out.println("Application Deadline: " + job.deadline);

        System.out.print("\nDo you want to proceed with the application? (Y/N): ");
        if (!scanner.nextLine().equalsIgnoreCase("Y")) {
            System.out.println("Application canceled. Returning to menu.");
            return;
        }

        // Confirm the user details to apply
        System.out.println("\nConfirm Your Details to Apply:");
        System.out.println("--------------------------------");
        System.out.println("Phone Number    : " + phone);
        System.out.println("Experience Level: " + experience);

        System.out.print("Is this information correct? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("N")) {
            System.out.print("Enter updated Phone Number (or press Enter to keep the current one): ");
            String updatedPhone = scanner.nextLine();
            if (!updatedPhone.isEmpty()) {
                if (!isValidPhone(updatedPhone)) {
                    System.out.println("Invalid phone number. It must be exactly 10 digits.");
                    return;
                }
                phone = updatedPhone;  // Update phone number
            }

            System.out.print("Enter updated Experience Level (or press Enter to keep the current one): ");
            String updatedExperience = scanner.nextLine();
            if (!updatedExperience.isEmpty()) {
                experience = updatedExperience;  // Update experience
            }
        }

        // Apply for the job
        System.out.println("\nApplication Submitted Successfully!");
        System.out.println("A confirmation email has been sent to your email address.");

        // Send confirmation email
        generateEmail(job, phone, experience, skills);
    } 
    static void generateEmail(Job job, String phone, String experience, String skills) {
        String emailContent = """
                Dear Applicant,

                Thank you for applying to the position of %s at %s. 
                Here are the details of the job you applied for:
                -------------------------------------
                Job Title: %s
                Company: %s
                Required Skills: %s
                Experience: %s years
                Deadline: %s
                -------------------------------------

                Please be prepared to meet us on the application deadline: %s.
                Ensure you bring all necessary documents and be ready for further instructions.

                Best regards,
                The Recruitment Team
                
                ===========================================================================
                """.formatted(
                job.title,
                job.companyName,  // Add company name
                job.title,
                job.companyName,  // Add company name here as well
                job.skills,
                job.experience,
                job.deadline,
                job.deadline
        );

        System.out.println("\nGenerated Email");
        System.out.println("===========================================================================");
        System.out.println(emailContent);
    }
    static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email.matches(emailRegex); // Simply return true or false without printing messages
    }

    static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(passwordRegex); // Simply return true or false without printing messages
    }

    static boolean isValidPhone(String phone) {
        if (phone.matches("\\d{10}")) {
            System.out.println("Valid phone number!");
            return true;
        } else {
            System.out.println("Invalid phone number. It must be exactly 10 digits.");
            return false;
        }
    }
    static class Job {
        String title, skills, experience, deadline, companyName;

        Job(String title, String skills, String experience, String deadline, String companyName) {
            this.title = title;
            this.skills = skills; 
            this.experience = experience;
            this.deadline = deadline;
            this.companyName = companyName;
        }

        boolean matchesSkills(String[] userSkills) {
            String[] jobSkills = skills.split(",\\s*"); 
            for (String userSkill : userSkills) { 
                boolean matched = false;
                for (String jobSkill : jobSkills) {  
                    if (jobSkill.trim().equalsIgnoreCase(userSkill.trim())) {  
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    return false;  
                }
            }
            return true; 
        }

        @Override
        public String toString() {
            return "-------------------------------------\n" +
                   "Company        : " + companyName + "\n" +
                   "Job Title      : " + title + "\n" +
                   "Required Skills: " + skills + "\n" +
                   "Experience     : " + experience + " years\n" +
                   "Deadline       : " + deadline + "\n" +
                   "-------------------------------------";
        }
    }
 }

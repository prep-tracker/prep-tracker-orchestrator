package com.prept.tracker.service;

import com.prept.tracker.domain.entity.*;
import com.prept.tracker.domain.enums.*;
import com.prept.tracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoDataSeederService {

    private final CategoryRepository categoryRepository;
    private final ResourceRepository resourceRepository;
    private final PreparationPlanRepository preparationPlanRepository;
    private final StudySessionRepository studySessionRepository;
    private final CertificationRepository certificationRepository;
    private final GoalRepository goalRepository;
    private final RoadmapRepository roadmapRepository;
    private final NoteRepository noteRepository;
    private final RevisionRepository revisionRepository;
    private final InterviewTopicRepository interviewTopicRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void seedDemoData(User user) {
        // 1. Fetch categories
        Category javaCategory = categoryRepository.findByName("Java").orElse(null);
        Category springBootCategory = categoryRepository.findByName("Spring Boot").orElse(null);
        Category dsaCategory = categoryRepository.findByName("DSA").orElse(null);

        // 2. Resources
        Resource concurrencyBook = Resource.builder()
                .name("Mastering Java Concurrency")
                .type(ResourceType.BOOK)
                .url("https://example.com/java-concurrency")
                .author("Brian Goetz")
                .estimatedHours(15)
                .difficulty(Difficulty.INTERMEDIATE)
                .notes("Focus on executor frameworks and memory models.")
                .user(user)
                .category(javaCategory)
                .build();
        concurrencyBook.getTags().addAll(List.of("Java", "Concurrency"));
        concurrencyBook = resourceRepository.save(concurrencyBook);

        Resource springBootCourse = Resource.builder()
                .name("Spring Boot 3.x Masterclass")
                .type(ResourceType.UDEMY_COURSE)
                .url("https://udemy.com/springboot-masterclass")
                .author("John Doe")
                .estimatedHours(30)
                .difficulty(Difficulty.INTERMEDIATE)
                .notes("Excellent course covering auto-configuration, security and microservices.")
                .user(user)
                .category(springBootCategory)
                .build();
        springBootCourse.getTags().addAll(List.of("Spring Boot", "Microservices"));
        springBootCourse = resourceRepository.save(springBootCourse);

        Resource leetcodePlatform = Resource.builder()
                .name("LeetCode 75 Study Plan")
                .type(ResourceType.PRACTICE_PLATFORM)
                .url("https://leetcode.com/study-plan/leetcode-75")
                .author("LeetCode")
                .estimatedHours(50)
                .difficulty(Difficulty.ADVANCED)
                .notes("Crucial DSA questions covering Arrays, Sliding Windows, Trees, and Graphs.")
                .user(user)
                .category(dsaCategory)
                .build();
        leetcodePlatform.getTags().addAll(List.of("DSA", "Algorithms"));
        leetcodePlatform = resourceRepository.save(leetcodePlatform);

        // 3. Preparation Plans
        PreparationPlan concurrencyPlan = PreparationPlan.builder()
                .goal("Complete Concurrency Book Chapters 1 to 8")
                .resource(concurrencyBook)
                .targetCompletionDate(LocalDate.now().plusWeeks(1))
                .estimatedHours(15)
                .priority(PlanPriority.MEDIUM)
                .status(PlanStatus.IN_PROGRESS)
                .progress(40)
                .user(user)
                .build();
        preparationPlanRepository.save(concurrencyPlan);

        PreparationPlan springBootPlan = PreparationPlan.builder()
                .goal("Complete Spring Boot Course and Build Microservice Project")
                .resource(springBootCourse)
                .targetCompletionDate(LocalDate.now().plusWeeks(3))
                .estimatedHours(30)
                .priority(PlanPriority.HIGH)
                .status(PlanStatus.IN_PROGRESS)
                .progress(20)
                .user(user)
                .build();
        preparationPlanRepository.save(springBootPlan);

        // 4. Study Sessions
        StudySession concurrencySession = StudySession.builder()
                .sessionDate(LocalDate.now().minusDays(1))
                .durationMinutes(120)
                .topicCovered("Thread Pools & Executors")
                .notes("ThreadPoolExecutor configuration and queueing strategies.")
                .resource(concurrencyBook)
                .user(user)
                .build();
        studySessionRepository.save(concurrencySession);

        StudySession springBootSession = StudySession.builder()
                .sessionDate(LocalDate.now())
                .durationMinutes(180)
                .topicCovered("Spring Boot Auto-configuration")
                .notes("Explored @ConditionalOnProperty and custom configuration properties mapping.")
                .resource(springBootCourse)
                .user(user)
                .build();
        studySessionRepository.save(springBootSession);

        StudySession leetcodeSession = StudySession.builder()
                .sessionDate(LocalDate.now().minusDays(3))
                .durationMinutes(90)
                .topicCovered("Sliding Window Problems")
                .notes("Solved 3 sliding window problems on LeetCode.")
                .resource(leetcodePlatform)
                .user(user)
                .build();
        studySessionRepository.save(leetcodeSession);

        // 5. Certifications
        Certification awsCert = Certification.builder()
                .name("AWS Certified Solutions Architect - Associate")
                .provider("Amazon Web Services")
                .examDate(LocalDate.now().plusMonths(1))
                .targetDate(LocalDate.now().plusWeeks(3))
                .cost(new java.math.BigDecimal("150.00"))
                .progress(60)
                .status(CertificationStatus.IN_PROGRESS)
                .user(user)
                .build();
        certificationRepository.save(awsCert);

        Certification springCert = Certification.builder()
                .name("Spring Certified Professional")
                .provider("Broadcom / VMware")
                .targetDate(LocalDate.now().plusMonths(2))
                .cost(new java.math.BigDecimal("250.00"))
                .progress(0)
                .status(CertificationStatus.NOT_STARTED)
                .user(user)
                .build();
        certificationRepository.save(springCert);

        // 6. Goals
        Goal studyGoal = Goal.builder()
                .title("Study 10 Hours This Week")
                .description("Aim for consistent daily learning goals (approx 1.5h per day).")
                .startDate(LocalDate.now().minusDays(3))
                .targetDate(LocalDate.now().plusDays(4))
                .progress(65)
                .status(GoalStatus.ACTIVE)
                .user(user)
                .build();
        goalRepository.save(studyGoal);

        Goal leetcodeGoal = Goal.builder()
                .title("Solve 50 LeetCode Medium Problems")
                .description("Aiming to build solid patterns intuition.")
                .startDate(LocalDate.now().minusDays(10))
                .targetDate(LocalDate.now().plusDays(20))
                .progress(40)
                .status(GoalStatus.ACTIVE)
                .user(user)
                .build();
        goalRepository.save(leetcodeGoal);

        // 7. Roadmaps
        Roadmap backendRoadmap = Roadmap.builder()
                .title("Backend Developer Path")
                .description("Structured learning path to master core backend development skills.")
                .user(user)
                .build();

        RoadmapItem item1 = RoadmapItem.builder()
                .title("Java Concurrency & Collections")
                .orderIndex(1)
                .completed(true)
                .roadmap(backendRoadmap)
                .build();
        RoadmapItem item2 = RoadmapItem.builder()
                .title("Spring Boot Core & JPA")
                .orderIndex(2)
                .completed(false)
                .roadmap(backendRoadmap)
                .build();
        RoadmapItem item3 = RoadmapItem.builder()
                .title("Docker Containerization")
                .orderIndex(3)
                .completed(false)
                .roadmap(backendRoadmap)
                .build();
        
        backendRoadmap.getItems().add(item1);
        backendRoadmap.getItems().add(item2);
        backendRoadmap.getItems().add(item3);
        roadmapRepository.save(backendRoadmap);

        // 8. Notes
        Note noteA = Note.builder()
                .title("Markdown Syntax Cheat Sheet")
                .content("Here is a quick cheat sheet for markdown syntax:\n- **Bold text**\n- *Italic text*\n- [Link](https://google.com)\n- Fenced code block:\n```java\nSystem.out.println(\"Hello World\");\n```")
                .user(user)
                .build();
        noteA.getTags().addAll(List.of("Markdown", "Reference"));
        noteRepository.save(noteA);

        Note noteB = Note.builder()
                .title("JPA Performance Best Practices")
                .content("Key points to remember for JPA performance:\n1. Use FetchType.LAZY for all associations to avoid unnecessary SELECT queries.\n2. Use join fetch or EntityGraphs to avoid the N+1 SELECT query problem.\n3. Keep transactions as short as possible.")
                .user(user)
                .build();
        noteB.getTags().addAll(List.of("JPA", "Hibernate", "Performance"));
        noteRepository.save(noteB);

        // 9. Revisions
        Revision concurrencyRevision = Revision.builder()
                .subject("Thread Pool Executor Configuration")
                .firstRevision(LocalDate.now().minusDays(1))
                .secondRevision(LocalDate.now().plusDays(1))
                .thirdRevision(LocalDate.now().plusDays(5))
                .nextReminder(LocalDate.now().plusDays(1))
                .user(user)
                .build();
        revisionRepository.save(concurrencyRevision);

        // 10. Interview Topics
        InterviewTopic multithreadingTopic = InterviewTopic.builder()
                .topic("Concurrency & Multi-threading")
                .totalQuestions(15)
                .solvedQuestions(8)
                .revisionCount(2)
                .difficulty(Difficulty.INTERMEDIATE)
                .user(user)
                .build();
        interviewTopicRepository.save(multithreadingTopic);

        InterviewTopic cachingTopic = InterviewTopic.builder()
                .topic("System Design - Distributed Caching")
                .totalQuestions(10)
                .solvedQuestions(4)
                .revisionCount(1)
                .difficulty(Difficulty.ADVANCED)
                .user(user)
                .build();
        interviewTopicRepository.save(cachingTopic);

        // 11. Notifications
        Notification welcomeNotification = Notification.builder()
                .type(NotificationType.IN_APP)
                .message("Welcome to your Preparation Tracker! We've pre-populated some sample data in each tab to help you get started.")
                .read(false)
                .user(user)
                .createdAt(Instant.now())
                .build();
        notificationRepository.save(welcomeNotification);
    }
}

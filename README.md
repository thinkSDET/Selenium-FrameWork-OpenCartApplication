# Selenium-Framework-Creation 🚀

## Overview
This framework is designed for automated testing of web applications using **Selenium WebDriver**, **TestNG**, and **Maven**. It supports parallel execution, robust error handling, and dynamic configuration management. The framework is modular, scalable, and incorporates best practices for test automation.

## 🎯 Key Features

- **Parallel Execution**: Supports parallel test execution using TestNG and ThreadLocal for thread-safe WebDriver instances.
- **Dynamic Configuration**: Utilizes `ConfigManager` and `ConfigReader` to manage environment-specific configurations (e.g., URLs, browsers).
- **Extensible Reporting**: Integrates ExtentReports for detailed test reporting, including screenshots on failure.
- **Custom Exceptions**: Implements `FrameworkException` for enhanced error handling and debugging.
- **Reusable Utilities**: Includes utility classes for common tasks like waiting, logging, and screenshot capture.
- **Data-Driven Testing**: Supports data-driven testing using TestNG's `@DataProvider`.
- **Headless Mode**: Configurable headless mode for browsers using `ConfigManager`.
- **Retry Mechanism**: Implements a retry mechanism for failed tests using `RetryAnalyzer` and `RetryListener`.

## 🗂️ Framework Structure

### 1. Packages

- **`testCases`**: Contains test classes (e.g., `MyInfoPageTest`, `LoginPageTest`).
- **`testBase`**: Contains base classes and utilities (e.g., `UIBaseTest`, `PageFactory`).
- **`pages`**: Contains Page Object Model (POM) classes for web pages.
- **`common`**: Contains reusable utility methods (e.g., `CommonMethods`, `WaitManager`).
- **`customException`**: Contains custom exceptions (e.g., `FrameworkException`).
- **`testData`**: Contains test data providers (e.g., `LoginTestData`).

### 2. Core Components

#### 2.1. `UIBaseTest`
**Purpose**: Base class for all test classes.

**Key Features**:
- Initializes and manages WebDriver instances using ThreadLocal for parallel execution.
- Sets up and tears down WebDriver before and after each test method.
- Handles browser initialization and navigation to the base URL.

#### 2.2. `PageFactory`
**Purpose**: Centralized factory for creating page objects.

**Key Features**:
- Provides methods to instantiate page objects (e.g., `loginPage()`, `myInfoPage()`).
- Ensures thread-safe access to WebDriver instances.

#### 2.3. `ConfigManager` and `ConfigReader`
**Purpose**: Manages configuration properties (e.g., browser, environment, URLs).

**Key Features**:
- Reads configuration from `config.properties` and system properties.
- Supports dynamic environment selection (QA, Preprod, Prod).
- Ensures thread-safe property loading.

#### 2.4. `BrowserManager`
**Purpose**: Handles browser initialization.

**Key Features**:
- Supports multiple browsers (Chrome, Firefox, Edge).
- Configurable headless mode.

#### 2.5. `WaitManager`
**Purpose**: Manages explicit and implicit waits.

**Key Features**:
- Uses `WebDriverWait` for dynamic element waiting.
- Includes utility methods for common wait scenarios (e.g., visibility, clickability).

#### 2.6. `CommonMethods`
**Purpose**: Provides reusable utility methods for common Selenium operations.

**Key Features**:
- Methods for clicking, scrolling, switching frames, and clearing input fields.
- Supports JavaScript-based interactions for edge cases.

#### 2.7. `ExtentReportManager`
**Purpose**: Manages ExtentReports for test reporting.

**Key Features**:
- Generates HTML reports with detailed logs and screenshots.
- Supports parallel execution using ThreadLocal.

#### 2.8. `RetryAnalyzer` and `RetryListener`
**Purpose**: Implements a retry mechanism for failed tests.

**Key Features**:
- Retries failed tests up to a specified limit.
- Automatically applies retry logic to all test methods.

### 3. Test Execution Flow

**Initialization**:
- `UIBaseTest` initializes the WebDriver and navigates to the base URL.
- `PageFactory` creates page objects for the test.

**Test Execution**:
- Test methods interact with page objects and perform assertions.
- `WaitManager` ensures elements are ready before interaction.
- `CommonMethods` handles common Selenium operations.

**Reporting**:
- `ExtentReportManager` logs test steps and captures screenshots on failure.

**Teardown**:
- WebDriver is closed, and resources are cleaned up.

### 4. Configuration Management

- `config.properties`: Contains environment-specific configurations (e.g., URLs, browser settings).
- **Maven Profiles**: Define environment-specific properties (e.g., QA, Preprod, Prod) and test suites (e.g., Smoke, Regression).

### 5. Logging

- **Log4j2**: Provides detailed logging for debugging and monitoring.
- **Log Format**: Includes timestamp, log level, class name, and message.

### 6. Parallel Execution

- **TestNG XML**: Configures parallel execution at the method or test level.
- **ThreadLocal**: Ensures thread-safe WebDriver instances.

### 7. Unique Selling Points (USPs)

- **Thread-Safe Design**: Uses ThreadLocal for parallel execution, ensuring no conflicts between threads.
- **Dynamic Configuration**: Supports multiple environments and browsers without code changes.
- **Extensible Reporting**: Generates detailed reports with screenshots, making debugging easier.
- **Custom Exceptions**: Improves error handling and debugging with `FrameworkException`.
- **Retry Mechanism**: Automatically retries failed tests, improving test stability.

## 🚀 How to Use the Framework

### Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/Selenium-Framework-Creation.git

## 🔧 Setup & Installation

### Prerequisites
Ensure you have the following installed:

- **Java 11+**
- **Maven**
- **IntelliJ IDEA / Eclipse**
- **Git**


# Test Case Generator

This project is a Spring Boot web application that uses OpenAI's GPT-3.5 API to generate test cases based on user-provided user stories. The generated test cases can be saved to an Excel file.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Test Case Generator application allows users to input a user story and receive generated test cases. The application leverages OpenAI's GPT-3.5 API to generate the test cases and uses Apache POI to save them to an Excel file.

## Features

- Input user stories through a web interface
- Generate test cases using OpenAI's GPT-3.5 API
- Save generated test cases to an Excel file

## Installation

### Prerequisites

- Java 11 or later
- Maven
- An OpenAI API key

### Steps

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/test-case-generator.git
    cd test-case-generator
    ```

2. Update the `application.properties` file with your OpenAI API key:

    ```properties
    openai.api.key=your_openai_api_key
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

## Usage

1. Navigate to `http://localhost:8080` in your web browser.
2. Enter a user story in the provided textarea and click "Generate Test Cases".
3. The generated test cases will be displayed on the page.
4. Click "Save to Excel" to download the test cases as an Excel file.

## Configuration

### OpenAI API Key

Update the `application.properties` file with your OpenAI API key:

```properties
openai.api.key=your_openai_api_key

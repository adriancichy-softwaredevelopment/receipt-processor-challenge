# Receipt Processor
The backend take-home exercise for the Backend Engineer opportunity at Fetch.

## Setup Instructions

### Prerequisites
- **Docker**: Ensure Docker is installed and running on your machine.

### Running the Application with Docker
   
1. Run the following command from the root directory to build the Docker image:
   ```bash
   docker build -t receipt-processor -f docker/Dockerfile .
   ```

2. Run the Docker container:
   ```bash
   docker run --name receipt-processor-container -p 8080:8080 receipt-processor
   ```

3. Access the service at http://localhost:8080
# Obtendo a identidade da conta atual
data "aws_caller_identity" "current" {}

# Declarando o repositório ECR
resource "aws_ecr_repository" "ecr_repo" {
  name                 = "quick-serve-api-producao"
  image_tag_mutability = "MUTABLE"
  lifecycle {
    ignore_changes = [image_tag_mutability]
  }
}

# Definição da Task no ECS
resource "aws_ecs_task_definition" "app_task" {
  family                   = "quick-serve-api-producao"
  execution_role_arn       = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/ecsTaskExecutionRole"
  task_role_arn            = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:role/ecsTaskExecutionRole"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"

  container_definitions = jsonencode([
    {
      name      = "mongodb"
      image     = "mongo:latest"
      cpu       = 128
      memory    = 256
      essential = true
      portMappings = [
        {
          containerPort = 27017
          hostPort      = 27017
        }
      ]
    },
    {
      name      = "app"
      image     = "${aws_ecr_repository.ecr_repo.repository_url}:latest"
      cpu       = 128
      memory    = 256
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
        }
      ]
    },
    {
      name      = "mongo-express"
      image     = "mongo-express"
      cpu       = 128
      memory    = 256
      essential = true
      portMappings = [
        {
          containerPort = 8081
          hostPort      = 8081

# Docker Helper Script - Servants Management System
# PowerShell version for Windows users

param(
    [Parameter(Position=0)]
    [ValidateSet('start', 'stop', 'restart', 'logs', 'status', 'clean', 'rebuild', 'db-shell', 'backend-logs', 'frontend-logs', 'health', 'help')]
    [string]$Command = 'help'
)

$ProjectName = "servants-management"

function Write-Info {
    param([string]$Message)
    Write-Host "[INFO] $Message" -ForegroundColor Green
}

function Write-Warning {
    param([string]$Message)
    Write-Host "[WARNING] $Message" -ForegroundColor Yellow
}

function Write-Error {
    param([string]$Message)
    Write-Host "[ERROR] $Message" -ForegroundColor Red
}

function Show-Help {
    @"
Docker Helper Script - Servants Management System

Usage: .\docker-helper.ps1 [COMMAND]

Commands:
  start         - Build e start todos os containers
  stop          - Para todos os containers
  restart       - Restart todos os containers
  logs          - Mostra logs de todos os containers
  status        - Mostra status dos containers
  clean         - Para e remove containers, volumes e imagens
  rebuild       - Rebuild completo sem cache
  db-shell      - Abre shell no PostgreSQL
  backend-logs  - Mostra logs do backend
  frontend-logs - Mostra logs do frontend
  health        - Verifica health dos serviços
  help          - Mostra esta mensagem

Examples:
  .\docker-helper.ps1 start
  .\docker-helper.ps1 logs
  .\docker-helper.ps1 health

"@
}

function Test-Docker {
    if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
        Write-Error "Docker não está instalado!"
        exit 1
    }

    if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)) {
        Write-Error "Docker Compose não está instalado!"
        exit 1
    }
}

function Start-Services {
    Write-Info "Iniciando serviços..."
    docker-compose up --build -d
    Write-Info "Serviços iniciados!"
    Write-Info "Frontend: http://localhost"
    Write-Info "Backend: http://localhost:8080"
    Write-Info "Swagger: http://localhost:8080/swagger-ui.html"
}

function Stop-Services {
    Write-Info "Parando serviços..."
    docker-compose stop
    Write-Info "Serviços parados!"
}

function Restart-Services {
    Write-Info "Reiniciando serviços..."
    docker-compose restart
    Write-Info "Serviços reiniciados!"
}

function Show-Logs {
    Write-Info "Mostrando logs (Ctrl+C para sair)..."
    docker-compose logs -f
}

function Show-Status {
    Write-Info "Status dos containers:"
    docker-compose ps
}

function Clean-All {
    Write-Warning "Esta operação irá remover containers, volumes e imagens!"
    $confirm = Read-Host "Tem certeza? (s/N)"
    if ($confirm -eq 's' -or $confirm -eq 'S') {
        Write-Info "Removendo tudo..."
        docker-compose down -v --rmi all
        Write-Info "Limpeza completa!"
    } else {
        Write-Info "Operação cancelada."
    }
}

function Rebuild-All {
    Write-Info "Rebuild completo sem cache..."
    docker-compose build --no-cache
    docker-compose up -d
    Write-Info "Rebuild concluído!"
}

function Open-DbShell {
    Write-Info "Abrindo shell PostgreSQL..."
    docker-compose exec postgres psql -U postgres -d servants_db
}

function Show-BackendLogs {
    Write-Info "Logs do backend (Ctrl+C para sair)..."
    docker-compose logs -f backend
}

function Show-FrontendLogs {
    Write-Info "Logs do frontend (Ctrl+C para sair)..."
    docker-compose logs -f frontend
}

function Test-Health {
    Write-Info "Verificando health dos serviços..."

    Write-Host ""
    Write-Info "PostgreSQL:"
    docker-compose exec postgres pg_isready -U postgres
    if ($LASTEXITCODE -ne 0) {
        Write-Error "PostgreSQL não está respondendo"
    }

    Write-Host ""
    Write-Info "Backend:"
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/health" -UseBasicParsing
        $response.Content | ConvertFrom-Json | ConvertTo-Json
    } catch {
        Write-Error "Backend não está respondendo"
    }

    Write-Host ""
    Write-Info "Frontend:"
    try {
        $response = Invoke-WebRequest -Uri "http://localhost/" -UseBasicParsing
        Write-Host "Status: $($response.StatusCode)"
    } catch {
        Write-Error "Frontend não está respondendo"
    }
}

# Main execution
Test-Docker

switch ($Command) {
    'start'         { Start-Services }
    'stop'          { Stop-Services }
    'restart'       { Restart-Services }
    'logs'          { Show-Logs }
    'status'        { Show-Status }
    'clean'         { Clean-All }
    'rebuild'       { Rebuild-All }
    'db-shell'      { Open-DbShell }
    'backend-logs'  { Show-BackendLogs }
    'frontend-logs' { Show-FrontendLogs }
    'health'        { Test-Health }
    'help'          { Show-Help }
    default         { Show-Help }
}

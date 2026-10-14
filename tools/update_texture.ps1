# update_texture.ps1
# Reemplaza una textura de item del Vodka Mod a partir de cualquier imagen.
# Redimensiona a 16x16 (el tamano que usa Minecraft) y la guarda en el mod.
#
# Uso:
#   powershell -ExecutionPolicy Bypass -File tools\update_texture.ps1 -Source "C:\ruta\mi_botella.png" -Target vodka_bottle
#
# Nombres validos de -Target: vodka_bottle, vodka, premium_vodka, broken_bottle

param(
    [Parameter(Mandatory = $true)]
    [string]$Source,

    [Parameter(Mandatory = $true)]
    [ValidateSet('vodka_bottle', 'vodka', 'premium_vodka', 'broken_bottle')]
    [string]$Target,

    [int]$Size = 16
)

Add-Type -AssemblyName System.Drawing

if (-not (Test-Path $Source)) {
    Write-Error "No existe el archivo de origen: $Source"
    exit 1
}

$projectRoot = Split-Path -Parent $PSScriptRoot
$destDir = Join-Path $projectRoot 'src\main\resources\assets\vodka_mod\textures\item'
$dest = Join-Path $destDir "$Target.png"

$srcImg = [System.Drawing.Image]::FromFile($Source)
$bmp = New-Object System.Drawing.Bitmap $Size, $Size
$g = [System.Drawing.Graphics]::FromImage($bmp)
$g.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::NearestNeighbor
$g.PixelOffsetMode = [System.Drawing.Drawing2D.PixelOffsetMode]::Half
$g.DrawImage($srcImg, 0, 0, $Size, $Size)
$g.Dispose()

$bmp.Save($dest, [System.Drawing.Imaging.ImageFormat]::Png)
$bmp.Dispose()
$srcImg.Dispose()

Write-Host "Textura actualizada: $dest" -ForegroundColor Green
Write-Host "Reinicia runClient o pulsa F3+T en el juego para verla." -ForegroundColor Yellow
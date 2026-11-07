import re

manifest_path = 'app/src/main/AndroidManifest.xml'

with open(manifest_path, 'r') as f:
    content = f.read()

# 1. Buscar la MainActivity y quitarle el intent-filter de Lanzador
main_activity_regex = r'(<activity[^>]*?android:name="\.activity\.MainActivity"[^>]*?>)(.*?)(</activity>)'
match = re.search(main_activity_regex, content, re.DOTALL)

if match:
    tag = match.group(1)
    inner = match.group(2)
    end_tag = match.group(3)
    
    # Quitar el intent-filter de MainActivity y poner exported=false
    new_inner = re.sub(r'<intent-filter>.*?</intent-filter>', '', inner, flags=re.DOTALL)
    tag = tag.replace('android:exported="true"', 'android:exported="false"')
    new_main = tag + new_inner + end_tag
    
    # 2. Crear la LicenseActivity como la nueva pantalla de inicio
    license_activity = '''
        <activity
            android:name=".activity.LicenseActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    '''
    
    # Reemplazar en el archivo
    new_content = content.replace(match.group(0), license_activity + new_main)
    
    with open(manifest_path, 'w') as f:
        f.write(new_content)
    print("✅ Manifest modificado: LicenseActivity es ahora la pantalla de inicio.")
else:
    print("❌ No se encontró MainActivity. Revisa el archivo manualmente.")

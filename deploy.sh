gradlew.bat clean
gradlew.bat bootJar
scp -r build root@178.128.197.32:~/sociaza
ssh root@178.128.197.32
cd sociaza
#echo Type the gpg password:
#read gpg_password
gpg --output passwd_db --decrypt resources/passwd_db.gpg --recipient mlgubolin@hey.com
gpg --output passwd_mail --decrypt resources/passwd_mail.gpg --recipient mlgubolin@hey.com
password_db =`cat passwd_db`
password_mail =`cat passwd_mail`
export DATABASE_PASSWORD=password_db
export EMAIL_PASSWORD=password_mail
java -jar libs/sociaza*.jar
Tạo Roles trong DB trước:
ROLE_ADMIN,
ROLE_USER

# Sign Up -> /api/auth/signup
{
	"firstName": "alex",
	"lastName": "wang",
	"username": "alexvu",
	"password": "123456",
	"email": "alexvuth@gmail.com"
}

# Log In -> /api/auth/signin
{
	"usernameOrEmail": "alexvu",
	"password": "123456"
}

import requests


if __name__=='__main__':
	while(True):
		requests.post('http://localhost:8080/notify')

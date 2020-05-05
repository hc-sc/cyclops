FROM frolvlad/alpine-python-machinelearning

RUN apk add --no-cache build-base jpeg-dev zlib-dev python3-dev gcc bash
ENV LIBRARY_PATH=/lib:/usr/lib
RUN pip3 install --upgrade pip
COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

#testing
#CMD ["tail", "-F", "/dev/null"]

# Working
#CMD ["python", "/app/main.py"]

RUN mkdir /uploads

# production
COPY app /app
WORKDIR /app
CMD ["gunicorn", "--timeout", "300", "main:app", "--reload", "-b", "0.0.0.0:8000"]

FROM anibali/pytorch:cuda-10.1
USER root

ENV LIBRARY_PATH=/lib:/usr/lib

WORKDIR /tmp
RUN apt-get update && \
    apt-get install -y \
        imagemagick \
        python-opencv \
        tesseract-ocr \
        libtesseract-dev \
        g++ \
        autoconf \
        automake \
        libtool \
        autoconf-archive \
        pkg-config \
        libpng-dev \
        libjpeg8-dev \
        libtiff5-dev \
        zlib1g-dev \
        libicu-dev \
        libpango1.0-dev \
        libcairo2-dev \
        make \
        wget \
        tar \
    && \
        rm -rf /var/lib/apt/lists/* 

RUN wget http://www.leptonica.org/source/leptonica-1.79.0.tar.gz && \
    tar xvf leptonica-1.79.0.tar.gz && \
    rm -f leptonica-1.79.0.tar.gz && \
    cd leptonica-1.79.0 && \
    ./configure && \
    make && \
    make install 

WORKDIR /tmp/
RUN git clone https://github.com/tesseract-ocr/tesseract.git && \
    cd tesseract && \
    git checkout cdebe13 && \
    ./autogen.sh && \
    ./configure --enable-debug && \
    LDFLAGS="-L/usr/local/lib" CFLAGS="-I/usr/local/include" make && \
    LDFLAGS="-L/usr/local/lib" CFLAGS="-I/usr/local/include" make install && \
    LDFLAGS="-L/usr/local/lib" CFLAGS="-I/usr/local/include" ldconfig 


RUN wget https://github.com/tesseract-ocr/tessdata/raw/master/fra.traineddata \
         -O /usr/local/share/tessdata/fra.traineddata && \
    wget https://github.com/tesseract-ocr/tessdata/raw/master/eng.traineddata \
         -O /usr/local/share/tessdata/eng.traineddata 

WORKDIR /tmp
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt

ENV LC_ALL=C.UTF-8
ENV LANG=C.UTF-8

#RUN wget https://dl.min.io/client/mc/release/linux-amd64/mc && chmod +x mc && mv mc /bin/mc

RUN mkdir -p /uploads

COPY app /app
WORKDIR /app
CMD ["uvicorn", "main:app", "--reload", "--host", "0.0.0.0", "--port", "9000"]

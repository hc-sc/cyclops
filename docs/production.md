# Architecture

The docker-compose file at the root of the repository ties together 4 services
(`docker` is explained in the next section)

1. webapp
2. mapi
3. minio  (could be replaced with an s3 bucket)
4. mongo  (may be removed in the future)

The first two, `webapp` and `mapi`, are the core of the application. 

## Webapp

The `webapp` is a Flask application which drives the web UI. For the scale of
this project, you'll probably only need one copy of the webapp running. Mapi requests data from
`mapi` via API calls.


## Mapi

The `mapi` is the machine learning api responsible for the OCR and computations.
Where the webapp is very lightweight, `mapi` is very demanding computationally.

For this reason, the application was designed so that there could be one instance of
`webapp` running on a server, but with multiple `mapi` instances running on different servers,
to distribute the load if multiple analysts are using the application at once. Otherwise, 
if many people were using the application at once, `mapi` could easily overwhelm even a
reasonably large machine. This approach will require a load balancer or a kubernetes configuration.
Either will be easy to implement, because the application is already containerized.

## Mongo & Minio

Mongo is a small database which we can use as a cache. It is being used to optimize some code at the moment, but it may not be needed in the future, and then it might be removed.

Minio offers S3 style storage on a hard-disk. It is used to store the results and edits on all product proposals, as well as the images and OCR results, so that this data can be fed back into retraining at a later time. So Minio is just capturing and storing info that will be valuable long-term. It is compatible with S3, so you could replace it with S3 storage or Azure blob storage on your cloud provider, if you want.

# Docker

Docker basically gives you lightweight virtual machines that run a single application. 
When we say that there are four services/docker images, that means that these "images"
(which are ultimately just files that you store on your hard-drive), allow you to run the
application with `docker run your_image_name`. Importantly, any computer with docker can run
these images, and the dependencies are totally self-contained.

Both `webapp` and `mapi` have `Dockerfile`s which are templates to create the docker images
from the source code within this repository. The `docker-compose.yml` file references these images
(actually at the moment it references *a repository where it downloads the images from*), and it
defines environment variables for each image and it networks the images together so that they can
communicate with one-another.

# Kubernetes = Multi-machine docker-compose

Ultimately `docker-compose` should be replaced with a Kubernetes deployment in order to host these services. Kubernetes is a *Cluster Orchestrator*. You rent a number of cloud computers from your preferred cloud provider (AWS, Azure, GCP, Digital Ocean, etc) and then you usually use a Kubernetes managed service offered by them (Kubernetes was developed by Google, but is Open Source). Kubernetes is an abstraction layer over your number of machines, and you give it services in the form of little `yaml` files (like `json`, but indented). For example, this piece of yaml creates my website. 

```yaml
apiVersion: v1
kind: Service
namespace: default
metadata:
  name: bdrummond
spec:
  ports:
  - name: http-port
    port: 80
    targetPort: 80
  selector:
    app: bdrummond
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: bdrummond
spec:
  selector:
    matchLabels:
      app: bdrummond
  replicas: 1
  template:
    metadata:
      labels:
        app: bdrummond
    spec:
      containers:
      - name: bdrummond
        image: blairdrummond/www-personal-page
        ports:
        - containerPort: 80
        imagePullPolicy: Always
``` 

I would also add another little piece of yaml somewhere that points my url to this service inside the cluster. `blair.happylittlecloud.ca -> bdrummond.default.svc.cluster.local` (`svc = service`).


You run this with `kubectl apply -f bdrummond.yaml`. Then Kubernetes manages the rest. Your service is managed, auto-restarted, and even autoscaled by Kubernetes.


## Why we need autoscaling

`mapi` is huge. The computational requirements of it require either `a)` a really big machine, or `b)` a couple of medium sized machines. Kubernetes makes `b)` possible. And in fact, it lets you allocate the machines available dynamically, so that your cluster can automatically scale up to accommodate increased demand, and it can scale down while it isn't being utilized, which **significantly** reduces costs.


# CI/CD Pipeline

We are not using Kubernetes in this project at the moment because our cluster is only internally available (at least, that was the case when we started this project). It made our problem complicated. But the CI/CD pipeline looks like this:

1. We use Gitlab CI, and the configuration is in the `.gitlab-ci.yml` file. The file builds both `mapi` and `webapp` docker images, and pushed them into the gitlab container registry.
2. Once the images are pushed, we send a restart signal to the `cyclops-app.service` on a virtual machine running on Google Cloud Platform. That's a `systemd` service which runs our docker-compose file from the git repository; on restarting, the service pulls the images from our git repository, networks them with docker compose, and then the webserver `nginx` routes traffic to them. The details of this are in the `.deploy` folder.

This CI/CD gets run on every commit to the master branch, so the website always stays up to date. However, the set up is pretty intricate and not scalable. It was a temporary solution, since it requires careful nginx configuration, and it requires distributing git credentials to the server in order to pull from the container registry on gitlab, and it requires storing ssh keys inside of gitlab in order to log into the server while executing the CD scripts.




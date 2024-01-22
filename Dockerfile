FROM python:3

WORKDIR /app

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

RUN mkdir proto
ENV PYTHONPATH="/app/proto"
COPY nn-training-data-collector/build/generated/source/proto/main/python/* proto/
COPY entrypoint.py .

CMD [ "python", "entrypoint.py"]

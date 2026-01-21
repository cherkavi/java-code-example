#!/usr/bin/env python3
"""
Simple Python HTTP server that exposes POST /posts and logs every POST request to the console.
Usage:
    python3 server.py --port 8000
Then send POSTs to http://localhost:8000/posts
"""
import http.server
import socketserver
import json
from datetime import datetime
import argparse
from typing import Optional

class PostLoggingHandler(http.server.BaseHTTPRequestHandler):
    def do_POST(self):
        # Only accept /posts
        # if self.path != "/posts":
        #     self.send_response(404)
        #     self.end_headers()
        #     self.wfile.write(b"Not Found, expected: /posts")
        #     return

        # Read body
        content_length = int(self.headers.get("Content-Length", 0))
        body = self.rfile.read(content_length) if content_length > 0 else b""
        body_text = body.decode("utf-8", errors="replace")
        path = self.path

        # Try to parse JSON for pretty printing; fall back to raw text
        parsed_body: Optional[object]
        pretty_body: str
        try:
            parsed_body = json.loads(body_text) if body_text else None
            pretty_body = json.dumps(parsed_body, indent=2, ensure_ascii=False) if parsed_body is not None else ""
        except Exception:
            parsed_body = None
            pretty_body = body_text

        # Build a log entry and print to console
        log_entry = {
            "timestamp": datetime.utcnow().isoformat() + "Z",
            "client": self.client_address[0],
            "path": self.path,
            "method": "POST",
            "headers": dict(self.headers),
            "path": path,
            "body": parsed_body if parsed_body is not None else body_text,
        }

        print("\n--- POST request received ---")
        try:
            print(json.dumps(log_entry, indent=2, ensure_ascii=False))
        except Exception:
            # Fallback if something in headers isn't JSON-serializable
            print("Timestamp:", log_entry["timestamp"])
            print("Client:", log_entry["client"])
            print("Path:", log_entry["path"])
            print("Headers:")
            for k, v in log_entry["headers"].items():
                print(f"  {k}: {v}")
            print("Body:")
            print(pretty_body)
        print("-----------------------------\n")

        # Respond 200 OK with a small JSON body
        response = {"status": "ok", "received": True}
        resp_bytes = json.dumps(response).encode("utf-8")
        self.send_response(200)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(resp_bytes)))
        self.end_headers()
        self.wfile.write(resp_bytes)

    # Suppress default request logging (optional). Remove this method to enable standard logs.
    def log_message(self, format, *args):
        return

def run(port: int = 8000):
    with socketserver.TCPServer(("", port), PostLoggingHandler) as httpd:
        print(f"Serving HTTP on 0.0.0.0 port {port} (POST /posts will be logged). Ctrl-C to stop.")
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\nShutting down server.")
            httpd.server_close()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Simple POST-logging HTTP server")
    parser.add_argument("--port", type=int, default=8080, help="Port to listen on (default 8080)")
    args = parser.parse_args()
    run(port=args.port)
import { Component } from '@angular/core';
import {Stomp} from'@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mini Hearthstone';
  description = 'Angular-WebSocket Demo';

  greetings: string[] = [];
  disabled = true;
  name: string;
  private stompClient = null;

  constructor() {
  }

  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.greetings = [];
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/endpoint');
    this.stompClient = Stomp.over(socket);

    const _this = this;
    this.stompClient.connect({}, function (frame) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe('/topic/game', function (hello) {
        //_this.showGreeting(JSON.parse(hello.body).greeting);
      });

      _this.stompClient.subscribe('/user/queue/reply', function (resp) {
        console.log("server answer: "+resp.body)
      });

    });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.setConnected(false);
    console.log('Disconnected!');
  }

  sendName() {
    this.stompClient.send(
      '/hello',
      {},
      JSON.stringify({'name': this.name})
    );
  }

  connectToGame() {
    this.stompClient.send(
      '/connect',
      {}
    )
  }
  showGreeting(message) {
    this.greetings.push(message);
  }
}

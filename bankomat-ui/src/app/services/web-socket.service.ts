import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Stomp } from "@stomp/stompjs";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client!: Client;
  private messageSubject = new Subject<string>();

  constructor() {
    this.initializeWebSocketConnection();
  }

  public initializeWebSocketConnection(): void {
    const socket = new SockJS('http://localhost:8080/websocket');
    this.client = new Client({
      webSocketFactory: () => socket
    })

    this.client.onConnect = (frame) => {
      this.client.subscribe('/topic/processStatus', (message: IMessage) => {
        console.log("Message from server: ", message.body);
        this.messageSubject.next(message.body);
      });
    };

    this.client.activate();
  }

  public getMessage(): Subject<string> {
    return this.messageSubject;
  }
}

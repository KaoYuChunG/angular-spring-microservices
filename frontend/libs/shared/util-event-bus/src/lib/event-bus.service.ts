import { Injectable } from '@angular/core';
import { Subject, Observable, filter, map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EventBusService {
  private bus$ = new Subject<{ key: string; payload?: any }>();

  publish(key: string, payload?: any) {
    this.bus$.next({ key, payload });
  }

  on(key: string): Observable<any> {
    return this.bus$.asObservable().pipe(
      filter(event => event.key === key),
      map(event => event.payload)
    );
  }
}

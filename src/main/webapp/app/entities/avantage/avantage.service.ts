import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Avantage } from './avantage.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Avantage>;

@Injectable()
export class AvantageService {

    private resourceUrl =  SERVER_API_URL + 'api/avantages';

    constructor(private http: HttpClient) { }

    create(avantage: Avantage): Observable<EntityResponseType> {
        const copy = this.convert(avantage);
        return this.http.post<Avantage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(avantage: Avantage): Observable<EntityResponseType> {
        const copy = this.convert(avantage);
        return this.http.put<Avantage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Avantage>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Avantage[]>> {
        const options = createRequestOption(req);
        return this.http.get<Avantage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Avantage[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Avantage = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Avantage[]>): HttpResponse<Avantage[]> {
        const jsonResponse: Avantage[] = res.body;
        const body: Avantage[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Avantage.
     */
    private convertItemFromServer(avantage: Avantage): Avantage {
        const copy: Avantage = Object.assign({}, avantage);
        return copy;
    }

    /**
     * Convert a Avantage to a JSON which can be sent to the server.
     */
    private convert(avantage: Avantage): Avantage {
        const copy: Avantage = Object.assign({}, avantage);
        return copy;
    }
}

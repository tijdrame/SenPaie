import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MoisConcerne } from './mois-concerne.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MoisConcerne>;

@Injectable()
export class MoisConcerneService {

    private resourceUrl =  SERVER_API_URL + 'api/mois-concernes';

    constructor(private http: HttpClient) { }

    create(moisConcerne: MoisConcerne): Observable<EntityResponseType> {
        const copy = this.convert(moisConcerne);
        return this.http.post<MoisConcerne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(moisConcerne: MoisConcerne): Observable<EntityResponseType> {
        const copy = this.convert(moisConcerne);
        return this.http.put<MoisConcerne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MoisConcerne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MoisConcerne[]>> {
        const options = createRequestOption(req);
        return this.http.get<MoisConcerne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MoisConcerne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MoisConcerne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MoisConcerne[]>): HttpResponse<MoisConcerne[]> {
        const jsonResponse: MoisConcerne[] = res.body;
        const body: MoisConcerne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MoisConcerne.
     */
    private convertItemFromServer(moisConcerne: MoisConcerne): MoisConcerne {
        const copy: MoisConcerne = Object.assign({}, moisConcerne);
        return copy;
    }

    /**
     * Convert a MoisConcerne to a JSON which can be sent to the server.
     */
    private convert(moisConcerne: MoisConcerne): MoisConcerne {
        const copy: MoisConcerne = Object.assign({}, moisConcerne);
        return copy;
    }
}

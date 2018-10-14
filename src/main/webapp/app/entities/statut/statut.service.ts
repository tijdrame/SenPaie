import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Statut } from './statut.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Statut>;

@Injectable()
export class StatutService {

    private resourceUrl =  SERVER_API_URL + 'api/statuts';

    constructor(private http: HttpClient) { }

    create(statut: Statut): Observable<EntityResponseType> {
        const copy = this.convert(statut);
        return this.http.post<Statut>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(statut: Statut): Observable<EntityResponseType> {
        const copy = this.convert(statut);
        return this.http.put<Statut>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Statut>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Statut[]>> {
        const options = createRequestOption(req);
        return this.http.get<Statut[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Statut[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Statut = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Statut[]>): HttpResponse<Statut[]> {
        const jsonResponse: Statut[] = res.body;
        const body: Statut[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Statut.
     */
    private convertItemFromServer(statut: Statut): Statut {
        const copy: Statut = Object.assign({}, statut);
        return copy;
    }

    /**
     * Convert a Statut to a JSON which can be sent to the server.
     */
    private convert(statut: Statut): Statut {
        const copy: Statut = Object.assign({}, statut);
        return copy;
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bareme } from './bareme.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bareme>;

@Injectable()
export class BaremeService {

    private resourceUrl =  SERVER_API_URL + 'api/baremes';

    constructor(private http: HttpClient) { }

    create(bareme: Bareme): Observable<EntityResponseType> {
        const copy = this.convert(bareme);
        return this.http.post<Bareme>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bareme: Bareme): Observable<EntityResponseType> {
        const copy = this.convert(bareme);
        return this.http.put<Bareme>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bareme>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bareme[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bareme[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bareme[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bareme = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bareme[]>): HttpResponse<Bareme[]> {
        const jsonResponse: Bareme[] = res.body;
        const body: Bareme[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bareme.
     */
    private convertItemFromServer(bareme: Bareme): Bareme {
        const copy: Bareme = Object.assign({}, bareme);
        return copy;
    }

    /**
     * Convert a Bareme to a JSON which can be sent to the server.
     */
    private convert(bareme: Bareme): Bareme {
        const copy: Bareme = Object.assign({}, bareme);
        return copy;
    }
}

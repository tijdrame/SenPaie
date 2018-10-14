import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Convention } from './convention.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Convention>;

@Injectable()
export class ConventionService {

    private resourceUrl =  SERVER_API_URL + 'api/conventions';

    constructor(private http: HttpClient) { }

    create(convention: Convention): Observable<EntityResponseType> {
        const copy = this.convert(convention);
        return this.http.post<Convention>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(convention: Convention): Observable<EntityResponseType> {
        const copy = this.convert(convention);
        return this.http.put<Convention>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Convention>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Convention[]>> {
        const options = createRequestOption(req);
        return this.http.get<Convention[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Convention[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Convention = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Convention[]>): HttpResponse<Convention[]> {
        const jsonResponse: Convention[] = res.body;
        const body: Convention[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Convention.
     */
    private convertItemFromServer(convention: Convention): Convention {
        const copy: Convention = Object.assign({}, convention);
        return copy;
    }

    /**
     * Convert a Convention to a JSON which can be sent to the server.
     */
    private convert(convention: Convention): Convention {
        const copy: Convention = Object.assign({}, convention);
        return copy;
    }
}

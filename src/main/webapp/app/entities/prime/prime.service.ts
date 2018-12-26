import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Prime } from './prime.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Prime>;

@Injectable()
export class PrimeService {

    private resourceUrl =  SERVER_API_URL + 'api/primes';

    constructor(private http: HttpClient) { }

    create(prime: Prime): Observable<EntityResponseType> {
        const copy = this.convert(prime);
        return this.http.post<Prime>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prime: Prime): Observable<EntityResponseType> {
        const copy = this.convert(prime);
        return this.http.put<Prime>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Prime>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Prime[]>> {
        const options = createRequestOption(req);
        return this.http.get<Prime[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Prime[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Prime = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Prime[]>): HttpResponse<Prime[]> {
        const jsonResponse: Prime[] = res.body;
        const body: Prime[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Prime.
     */
    private convertItemFromServer(prime: Prime): Prime {
        const copy: Prime = Object.assign({}, prime);
        return copy;
    }

    /**
     * Convert a Prime to a JSON which can be sent to the server.
     */
    private convert(prime: Prime): Prime {
        const copy: Prime = Object.assign({}, prime);
        return copy;
    }
}

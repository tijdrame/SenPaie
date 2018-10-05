import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Exercice } from './exercice.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Exercice>;

@Injectable()
export class ExerciceService {

    private resourceUrl =  SERVER_API_URL + 'api/exercices';

    constructor(private http: HttpClient) { }

    create(exercice: Exercice): Observable<EntityResponseType> {
        const copy = this.convert(exercice);
        return this.http.post<Exercice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(exercice: Exercice): Observable<EntityResponseType> {
        const copy = this.convert(exercice);
        return this.http.put<Exercice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Exercice>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Exercice[]>> {
        const options = createRequestOption(req);
        return this.http.get<Exercice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Exercice[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Exercice = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Exercice[]>): HttpResponse<Exercice[]> {
        const jsonResponse: Exercice[] = res.body;
        const body: Exercice[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Exercice.
     */
    private convertItemFromServer(exercice: Exercice): Exercice {
        const copy: Exercice = Object.assign({}, exercice);
        return copy;
    }

    /**
     * Convert a Exercice to a JSON which can be sent to the server.
     */
    private convert(exercice: Exercice): Exercice {
        const copy: Exercice = Object.assign({}, exercice);
        return copy;
    }
}

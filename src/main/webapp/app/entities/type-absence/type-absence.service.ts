import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeAbsence } from './type-absence.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeAbsence>;

@Injectable()
export class TypeAbsenceService {

    private resourceUrl =  SERVER_API_URL + 'api/type-absences';

    constructor(private http: HttpClient) { }

    create(typeAbsence: TypeAbsence): Observable<EntityResponseType> {
        const copy = this.convert(typeAbsence);
        return this.http.post<TypeAbsence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeAbsence: TypeAbsence): Observable<EntityResponseType> {
        const copy = this.convert(typeAbsence);
        return this.http.put<TypeAbsence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeAbsence>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeAbsence[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeAbsence[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeAbsence[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeAbsence = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeAbsence[]>): HttpResponse<TypeAbsence[]> {
        const jsonResponse: TypeAbsence[] = res.body;
        const body: TypeAbsence[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeAbsence.
     */
    private convertItemFromServer(typeAbsence: TypeAbsence): TypeAbsence {
        const copy: TypeAbsence = Object.assign({}, typeAbsence);
        return copy;
    }

    /**
     * Convert a TypeAbsence to a JSON which can be sent to the server.
     */
    private convert(typeAbsence: TypeAbsence): TypeAbsence {
        const copy: TypeAbsence = Object.assign({}, typeAbsence);
        return copy;
    }
}

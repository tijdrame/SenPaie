import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeRelation } from './type-relation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeRelation>;

@Injectable()
export class TypeRelationService {

    private resourceUrl =  SERVER_API_URL + 'api/type-relations';

    constructor(private http: HttpClient) { }

    create(typeRelation: TypeRelation): Observable<EntityResponseType> {
        const copy = this.convert(typeRelation);
        return this.http.post<TypeRelation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeRelation: TypeRelation): Observable<EntityResponseType> {
        const copy = this.convert(typeRelation);
        return this.http.put<TypeRelation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeRelation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeRelation[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeRelation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeRelation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeRelation[]>): HttpResponse<TypeRelation[]> {
        const jsonResponse: TypeRelation[] = res.body;
        const body: TypeRelation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeRelation.
     */
    private convertItemFromServer(typeRelation: TypeRelation): TypeRelation {
        const copy: TypeRelation = Object.assign({}, typeRelation);
        return copy;
    }

    /**
     * Convert a TypeRelation to a JSON which can be sent to the server.
     */
    private convert(typeRelation: TypeRelation): TypeRelation {
        const copy: TypeRelation = Object.assign({}, typeRelation);
        return copy;
    }
}

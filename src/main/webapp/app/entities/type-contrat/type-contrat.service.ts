import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeContrat } from './type-contrat.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeContrat>;

@Injectable()
export class TypeContratService {

    private resourceUrl =  SERVER_API_URL + 'api/type-contrats';

    constructor(private http: HttpClient) { }

    create(typeContrat: TypeContrat): Observable<EntityResponseType> {
        const copy = this.convert(typeContrat);
        return this.http.post<TypeContrat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeContrat: TypeContrat): Observable<EntityResponseType> {
        const copy = this.convert(typeContrat);
        return this.http.put<TypeContrat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeContrat>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeContrat[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeContrat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeContrat[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeContrat = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeContrat[]>): HttpResponse<TypeContrat[]> {
        const jsonResponse: TypeContrat[] = res.body;
        const body: TypeContrat[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeContrat.
     */
    private convertItemFromServer(typeContrat: TypeContrat): TypeContrat {
        const copy: TypeContrat = Object.assign({}, typeContrat);
        return copy;
    }

    /**
     * Convert a TypeContrat to a JSON which can be sent to the server.
     */
    private convert(typeContrat: TypeContrat): TypeContrat {
        const copy: TypeContrat = Object.assign({}, typeContrat);
        return copy;
    }
}

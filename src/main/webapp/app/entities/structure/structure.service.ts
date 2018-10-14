import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Structure } from './structure.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Structure>;

@Injectable()
export class StructureService {

    private resourceUrl =  SERVER_API_URL + 'api/structures';

    constructor(private http: HttpClient) { }

    create(structure: Structure): Observable<EntityResponseType> {
        const copy = this.convert(structure);
        return this.http.post<Structure>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(structure: Structure): Observable<EntityResponseType> {
        const copy = this.convert(structure);
        return this.http.put<Structure>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Structure>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Structure[]>> {
        const options = createRequestOption(req);
        return this.http.get<Structure[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Structure[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Structure = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Structure[]>): HttpResponse<Structure[]> {
        const jsonResponse: Structure[] = res.body;
        const body: Structure[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Structure.
     */
    private convertItemFromServer(structure: Structure): Structure {
        const copy: Structure = Object.assign({}, structure);
        return copy;
    }

    /**
     * Convert a Structure to a JSON which can be sent to the server.
     */
    private convert(structure: Structure): Structure {
        const copy: Structure = Object.assign({}, structure);
        return copy;
    }
}

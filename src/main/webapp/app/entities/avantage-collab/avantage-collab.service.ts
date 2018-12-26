import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AvantageCollab } from './avantage-collab.model';
import { createRequestOption } from '../../shared';
import {Collaborateur} from "../collaborateur";

export type EntityResponseType = HttpResponse<AvantageCollab>;

@Injectable()
export class AvantageCollabService {

    private resourceUrl =  SERVER_API_URL + 'api/avantage-collabs';

    constructor(private http: HttpClient) { }

    create(avantageCollab: AvantageCollab): Observable<EntityResponseType> {
        const copy = this.convert(avantageCollab);
        return this.http.post<AvantageCollab>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(avantageCollab: AvantageCollab): Observable<EntityResponseType> {
        const copy = this.convert(avantageCollab);
        return this.http.put<AvantageCollab>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findByCollab(id: Collaborateur): Observable<HttpResponse<AvantageCollab[]>> {
        return this.http.get<AvantageCollab[]>(`${this.resourceUrl+"Ter"}/${id}`, { observe: 'response'})
            .map((res: HttpResponse<AvantageCollab[]>) => this.convertArrayResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AvantageCollab>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AvantageCollab[]>> {
        const options = createRequestOption(req);
        return this.http.get<AvantageCollab[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AvantageCollab[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AvantageCollab = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AvantageCollab[]>): HttpResponse<AvantageCollab[]> {
        const jsonResponse: AvantageCollab[] = res.body;
        const body: AvantageCollab[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AvantageCollab.
     */
    private convertItemFromServer(avantageCollab: AvantageCollab): AvantageCollab {
        const copy: AvantageCollab = Object.assign({}, avantageCollab);
        return copy;
    }

    /**
     * Convert a AvantageCollab to a JSON which can be sent to the server.
     */
    private convert(avantageCollab: AvantageCollab): AvantageCollab {
        const copy: AvantageCollab = Object.assign({}, avantageCollab);
        return copy;
    }
}

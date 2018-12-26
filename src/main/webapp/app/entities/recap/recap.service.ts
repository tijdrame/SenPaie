import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Recap } from './recap.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Recap>;

@Injectable()
export class RecapService {

    private resourceUrl =  SERVER_API_URL + 'api/recaps';

    constructor(private http: HttpClient) { }

    create(recap: Recap): Observable<EntityResponseType> {
        const copy = this.convert(recap);
        return this.http.post<Recap>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(recap: Recap): Observable<EntityResponseType> {
        const copy = this.convert(recap);
        return this.http.put<Recap>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Recap>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Recap[]>> {
        const options = createRequestOption(req);
        return this.http.get<Recap[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Recap[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Recap = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Recap[]>): HttpResponse<Recap[]> {
        const jsonResponse: Recap[] = res.body;
        const body: Recap[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Recap.
     */
    private convertItemFromServer(recap: Recap): Recap {
        const copy: Recap = Object.assign({}, recap);
        return copy;
    }

    /**
     * Convert a Recap to a JSON which can be sent to the server.
     */
    private convert(recap: Recap): Recap {
        const copy: Recap = Object.assign({}, recap);
        return copy;
    }
}

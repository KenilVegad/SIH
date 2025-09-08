'use client';

import { useState } from 'react';
import { Search, FileText, Plus, Calendar, Clock, Users } from 'lucide-react';
import Header from '../components/Header';

const amendments = [
  {
    id: 1,
    title: 'Draft Companies (Amendment) Rules, 2024',
    description: 'Proposed amendments to enhance corporate governance and director responsibilities.',
    category: 'Companies Act',
    status: 'Open',
    publishedDate: '2024-08-01',
    dueDate: '2024-09-30',
  },
  {
    id: 2,
    title: 'Taxation Laws (Second Amendment) Bill, 2024',
    description: 'Introduction of new sections to address tax evasion and promote digital transactions.',
    category: 'Taxation',
    status: 'Closed',
    publishedDate: '2024-07-15',
    dueDate: '2024-08-15',
  },
  {
    id: 3,
    title: 'Insolvency and Bankruptcy Code (Amendment) Bill, 2024',
    description: 'Strengthening the insolvency resolution process for corporate debtors.',
    category: 'IBC',
    status: 'Open',
    publishedDate: '2024-08-10',
    dueDate: '2024-10-15',
  },
  {
    id: 4,
    title: 'Limited Liability Partnership (Amendment) Rules, 2024',
    description: 'Simplifying the reporting and compliance framework for LLPs.',
    category: 'LLP Act',
    status: 'Open',
    publishedDate: '2024-08-20',
    dueDate: '2024-10-05',
  },
];

const getDeadlineProgress = (startDate, endDate) => {
    const start = new Date(startDate).getTime();
    const end = new Date(endDate).getTime();
    const today = new Date().getTime();
    if (today >= end) return 100;
    const total = end - start;
    const elapsed = today - start;
    return (elapsed / total) * 100;
  };

export default function AmendmentsProposalsPage() {
  const [openAmendmentId, setOpenAmendmentId] = useState(null);
  const [commentSubmitted, setCommentSubmitted] = useState(false);

  const handleToggleComments = (id) => {
    setOpenAmendmentId(openAmendmentId === id ? null : id);
    setCommentSubmitted(false);
  };

  const handleCommentSubmit = (e) => {
    e.preventDefault();
    setCommentSubmitted(true);
  };

  return (
    <div className="min-h-screen bg-white">
      <Header />
      <main className="container mx-auto px-4 py-8">
        <div className="bg-white p-6 rounded-xl shadow-md mb-8">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2">
              <label className="font-semibold text-gray-600 mb-2 block">Category</label>
              <div className="flex flex-wrap gap-2">
                <button className="px-4 py-2 bg-blue-600 text-white rounded-lg text-sm shadow hover:bg-blue-700">All</button>
                <button className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg text-sm hover:bg-gray-300">Companies Act</button>
                <button className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg text-sm hover:bg-gray-300">LLP Act</button>
                <button className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg text-sm hover:bg-gray-300">Regulatory Proposals</button>
              </div>
            </div>
            <div className="relative">
                <label className="font-semibold text-gray-600 mb-2 block">Search</label>
                 <input type="text" placeholder="Search Amendments..." className="w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                <Search className="absolute left-3 top-10 text-gray-400" size={20}/>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {amendments.map((amendment) => {
            const progress = getDeadlineProgress(amendment.publishedDate, amendment.dueDate);
            return (
            <div key={amendment.id} className="bg-white rounded-xl shadow-lg hover:shadow-2xl transition-shadow duration-300 border border-gray-200">
              <div className="p-6">
                <div className="flex justify-between items-start mb-4">
                    <h2 className="text-2xl font-bold text-blue-900">{amendment.title}</h2>
                    {amendment.status === 'Open' ? (
                        <span className="bg-green-100 text-green-700 font-semibold px-3 py-1 rounded-full">Open</span>
                    ) : (
                        <span className="bg-red-100 text-red-700 font-semibold px-3 py-1 rounded-full">Closed</span>
                    )}
                </div>
                <p className="text-gray-600 mb-6">{amendment.description}</p>
                
                <div className="grid grid-cols-2 gap-4 text-sm mb-6">
                    <div className="flex items-center text-gray-500"><FileText size={16} className="mr-2"/> Category: <span className="font-semibold text-gray-700 ml-1">{amendment.category}</span></div>
                    <div className="flex items-center text-gray-500"><Calendar size={16} className="mr-2"/> Published: <span className="font-semibold text-gray-700 ml-1">{amendment.publishedDate}</span></div>
                    <div className="flex items-center text-gray-500"><Clock size={16} className="mr-2"/> Deadline: <span className="font-semibold text-red-600 ml-1">{amendment.dueDate}</span></div>
                    <div className="flex items-center text-gray-500"><Users size={16} className="mr-2"/> Stakeholders: <span className="font-semibold text-gray-700 ml-1">Public, Companies, LLPs</span></div>
                </div>

                {amendment.status === 'Open' && (
                    <div className="mb-6">
                        <div className="w-full bg-gray-200 rounded-full h-2.5">
                            <div className="bg-blue-600 h-2.5 rounded-full" style={{ width: `${progress}%` }}></div>
                        </div>
                    </div>
                )}

                <div className="flex items-center space-x-4">
                  <button className="bg-blue-800 text-white px-6 py-2 rounded-lg hover:bg-blue-900 transition-colors font-semibold">View Details</button>
                  <button onClick={() => handleToggleComments(amendment.id)} className="bg-teal-600 text-white px-6 py-2 rounded-lg hover:bg-teal-700 transition-colors flex items-center font-semibold">
                    <Plus size={18} className="mr-2"/> Submit Feedback
                  </button>
                </div>
              </div>

              {openAmendmentId === amendment.id && (
                <div className="p-6 border-t bg-gray-50">
                  {commentSubmitted ? (
                    <div className="text-center py-8">
                      <p className="text-2xl font-semibold text-green-600">✅ Feedback submitted successfully!</p>
                    </div>
                  ) : (
                    <form onSubmit={handleCommentSubmit} className="space-y-4">
                      <h3 className="text-xl font-bold text-gray-800 mb-4">Submit Your Feedback</h3>
                      <textarea placeholder="Your detailed feedback..." rows="5" className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" required></textarea>
                      <div>
                        <label className="text-sm font-semibold text-gray-600">Attach Supporting Documents (Optional)</label>
                        <input type="file" className="w-full text-sm mt-1 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100" />
                      </div>
                      <button type="submit" className="w-full bg-green-600 text-white py-3 rounded-lg font-bold hover:bg-green-700 transition-transform transform hover:scale-105">Submit Feedback</button>
                    </form>
                  )}
                </div>
              )}
            </div>
            )
          })}
        </div>
      </main>
    </div>
  );
}
